package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.SentimentAnalysis;
import com.example.demo.entity.WordFreqAnalysis;
import com.example.demo.entity.dto.WordFreqDto;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.WordFreqAnalysisMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// 关于监测作品的评论的词频分析
@RequestMapping("/api/word-freq")
@RestController
@Api(tags = "词频统计控制器")
public class WordFreqAnalysisController {
    @Autowired
    private WordFreqAnalysisMapper wordFreqAnalysisMapper;

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // 查询所有的词频统计结果
    @GetMapping("/all")
    @ApiOperation(value = "查询所有词频统计结果")
    public Result findAll() {
        List<WordFreqAnalysis> wordFreqAnalyses = wordFreqAnalysisMapper.selectList(null);
        return Result.success(wordFreqAnalyses);
    }

    // 根据词频统计id查询词频统计结果
    @GetMapping("/id/{id}")
    @ApiOperation(value = "根据ID查询词频统计结果")
    public Result findById(@PathVariable Long id) {
        WordFreqAnalysis wordFreqAnalysis = wordFreqAnalysisMapper.selectById(id);
        return Result.success(wordFreqAnalysis);
    }

    // 分页 搜索查询
    @GetMapping("/byPage")
    @ApiOperation(value = "分页模糊查询词频统计结果")
    public Result findPage(@RequestParam(required = false, defaultValue = "") String searchWorkName,
                              @RequestParam(required = false, defaultValue = "") String searchCountry,
                              @RequestParam(required = false, defaultValue = "") String searchPlatform,
                              @RequestParam(required = false, defaultValue = "") String searchTime,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<WordFreqAnalysis> mapperPage = wordFreqAnalysisMapper
                .findPage(new Page<>(pageNum, pageSize),
                        searchWorkName,
                        searchCountry,
                        searchPlatform,
                        searchTime);
        return Result.success(mapperPage);
    }

    // 查询指定作品的词频统计结果
    @GetMapping("/query-res")
    @ApiOperation(value = "多条件精确查询词频统计结果")
    public Result getWordFreq(@RequestParam Integer searchWorkId,
                                 @RequestParam(required = false, defaultValue = "") String searchTime,
                                 @RequestParam(required = false, defaultValue = "") String searchCountry,
                                 @RequestParam(required = false, defaultValue = "") String searchPlatform,
                              @RequestParam(required = false, defaultValue = "50") Integer numLimit) throws ParseException {
        LambdaQueryWrapper<WordFreqAnalysis> query = Wrappers.<WordFreqAnalysis>lambdaQuery();
        query.eq(WordFreqAnalysis::getWorkId, searchWorkId);
        WordFreqDto.WordFreqDtoBuilder builder = WordFreqDto.builder();
        if (!"".equals(searchTime)) {
            query.eq(WordFreqAnalysis::getTime, searchTime);
            builder.time(sdf.parse(searchTime));
        }
        if (!"".equals(searchCountry)) {
            query.eq(WordFreqAnalysis::getCountry, searchCountry);
            builder.country(searchCountry);
        }
        if (!"".equals(searchPlatform)) {
            query.eq(WordFreqAnalysis::getPlatform, searchPlatform);
            builder.platform(searchPlatform);
        }
        List<WordFreqAnalysis> wordFreqAnalyses = wordFreqAnalysisMapper.selectList(query);

        WordFreqDto freqDto = builder.workName(monitorWorkMapper.findWorkName(searchWorkId)).build();
        List<WordFreqDto.WordInfo> list = new ArrayList<>();
        List<String> words = new ArrayList<>();
        for (WordFreqAnalysis wordFreqAnalysis: wordFreqAnalyses) {
            String[] keywords = wordFreqAnalysis.getKeywords().split("\\s+");
            String[] polarity = wordFreqAnalysis.getPolarity().split("\\s+");
            String[] frequency = wordFreqAnalysis.getFrequency().split("\\s+");
//            System.out.println(Arrays.toString(keywords));
//            System.out.println(Arrays.toString(polarity));
//            System.out.println(Arrays.toString(frequency));
            for (int i = 0; i < keywords.length; i++) {
                if (!words.contains(keywords[i])) { // 保证关键词不重复
                    words.add(keywords[i]);
                    WordFreqDto.WordInfo wordInfo = new WordFreqDto.WordInfo(keywords[i], polarity[i],
                            Integer.valueOf(frequency[i]));
                    list.add(wordInfo);
                } else {
                    int idx = words.indexOf(keywords[i]);
                    WordFreqDto.WordInfo info = list.get(idx);
                    info.countsAdd(Integer.valueOf(frequency[i]));
                }
            }
        }
        list.sort(new Comparator<WordFreqDto.WordInfo>() {
            @Override
            public int compare(WordFreqDto.WordInfo o1, WordFreqDto.WordInfo o2) {
                return o2.getCounts() - o1.getCounts();
            }
        });
        if (list.size() > numLimit) {
            list = list.subList(0, numLimit - 1);
        }
        freqDto.setKeywords(list);
        return Result.success(freqDto);
    }

    // 根据id删除指定词频分析结果
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "根据ID删除词频统计结果")
    @ApiImplicitParam(name = "ids", value = "词频统计ID数组")
    public Result deleteById(@PathVariable Long[] ids) {
        int res = wordFreqAnalysisMapper.deleteBatchIds(Arrays.asList(ids)); // 兼容批量删除
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "该词频统计结果已经被删除了");
    }

    // 新增词频统计结果
    @PostMapping("/add")
    @ApiOperation(value = "新增词频统计结果")
    public Result add(@RequestBody WordFreqAnalysis wordFreqAnalysis) {
        int res = wordFreqAnalysisMapper.insert(wordFreqAnalysis);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "插入词频统计结果失败");
    }

    // 修改词频统计结果
    @PutMapping("/update")
    @ApiOperation(value = "修改词频统计结果")
    public Result update(@RequestBody WordFreqAnalysis wordFreqAnalysis) {
        int res = wordFreqAnalysisMapper.updateById(wordFreqAnalysis);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "修改词频统计结果失败");
    }

    /**
     * Excel导出
     *
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出所有词频统计结果")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<WordFreqAnalysis> all = wordFreqAnalysisMapper.findAll();
        for (WordFreqAnalysis wordFreqAnalysis : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("词频统计ID", wordFreqAnalysis.getId());
            row.put("所属作品ID", wordFreqAnalysis.getWorkId());
            row.put("所属作品名", wordFreqAnalysis.getMonitorWork().getName());
            row.put("所属作品类型", wordFreqAnalysis.getMonitorWork().getCategory());
            row.put("所属国家", wordFreqAnalysis.getCountry());
            row.put("所统计评论的发布时间", wordFreqAnalysis.getTime());
            row.put("所属平台", wordFreqAnalysis.getPlatform());
            row.put("情感极性数组", wordFreqAnalysis.getPolarity());
            row.put("高频词数组", wordFreqAnalysis.getKeywords());
            row.put("高频词频数数组", wordFreqAnalysis.getFrequency());
            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("词频统计信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * Excel导出  模板
     *
     * @param response 响应对象
     * @throws IOException IO异常
     */
    @RequestMapping(value = "/importTemplate", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出词频统计结果导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("所属作品ID", "");
        row.put("所属国家", "");
        row.put("所统计评论的发布时间", "");
        row.put("所属平台", "");
        row.put("情感极性数组", "");
        row.put("高频词数组", "");
        row.put("高频词频数数组", "");
        list.add(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("词频统计信息导入模板", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * Excel导入
     *
     * @param file Excel
     * @return 表格
     * @throws IOException
     */
    @PostMapping("/import")
    @ApiOperation(value = "导入词频统计结果")
    public Result upload(MultipartFile file) throws IOException, ParseException {
        InputStream inputStream = file.getInputStream();
        // 获取excel表中的每一行数据
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<WordFreqAnalysis> saveList = new ArrayList<>();
        for (List<Object> row : lists) { // 将数据保存到列表中
            WordFreqAnalysis wordFreqAnalysis = new WordFreqAnalysis();
            wordFreqAnalysis.setWorkId(Integer.valueOf(row.get(0).toString()));
            wordFreqAnalysis.setCountry(row.get(1).toString());
            wordFreqAnalysis.setTime(sdf.parse(row.get(2).toString()));
            wordFreqAnalysis.setPlatform(row.get(3).toString());
            wordFreqAnalysis.setPolarity(row.get(4).toString());
            wordFreqAnalysis.setKeywords(row.get(5).toString());
            wordFreqAnalysis.setFrequency(row.get(6).toString());
            saveList.add(wordFreqAnalysis);
        }
        for (WordFreqAnalysis wordFreqAnalysis : saveList) {
            wordFreqAnalysisMapper.insert(wordFreqAnalysis); // 插入数据
        }
        return Result.success();
    }
}
