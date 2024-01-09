package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.PolarityAnalysis;
import com.example.demo.entity.SentimentAnalysis;
import com.example.demo.entity.dto.PolarityDto;
import com.example.demo.entity.dto.PolarityStatisticsDto;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.PolarityAnalysisMapper;
import com.example.demo.service.PolarityAnalysisService;
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

// 监测作品的情感极性分析
@RequestMapping("/api/polarity")
@RestController
@Api(tags = "情感极性分析控制器")
public class PolarityAnalysisController {
    @Autowired
    private PolarityAnalysisMapper polarityAnalysisMapper;

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    @Autowired
    private PolarityAnalysisService polarityAnalysisService;

    // 查询所有的情感极性分析结果
    @GetMapping("/all")
    @ApiOperation(value = "查询所有情感极性分析结果")
    public Result findAll() {
        List<PolarityAnalysis> polarityAnalyses = polarityAnalysisMapper.findAll();
        return Result.success(polarityAnalyses);
    }

    // 根据id查询情感极性分析结果
    @GetMapping("/id/{id}")
    @ApiOperation(value = "根据ID查询情感极性分析结果")
    public Result findById(@PathVariable Long id) {
        PolarityAnalysis polarityAnalysis = polarityAnalysisMapper.selectById(id);
        return Result.success(polarityAnalysis);
    }

    // 分页 搜索查询
    @GetMapping("/byPage")
    @ApiOperation(value = "分页模糊查询情感极性分析结果")
    public Result findPage(@RequestParam(required = false, defaultValue = "") String searchWorkName,
                              @RequestParam(required = false, defaultValue = "") String searchCountry,
                              @RequestParam(required = false, defaultValue = "") String searchPlatform,
                              @RequestParam(required = false, defaultValue = "") String searchTime,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<PolarityAnalysis> mapperPage = polarityAnalysisMapper
                .findPage(new Page<>(pageNum, pageSize),
                        searchWorkName,
                        searchCountry,
                        searchPlatform,
                        searchTime);
        return Result.success(mapperPage);
    }

    // 精确多条件兼容查询
    @GetMapping("/query-list")
    @ApiOperation(value = "多条件精确查询情感极性分析结果")
    public Result findAllQuery(@RequestParam(required = false, defaultValue = "0") Integer searchWorkId,
                                  @RequestParam(required = false, defaultValue = "") String searchCountry,
                                  @RequestParam(required = false, defaultValue = "") String searchPlatform,
                                  @RequestParam(required = false, defaultValue = "") String searchTime) {
        QueryWrapper<PolarityAnalysis> query = new QueryWrapper<>();
        if (searchWorkId != 0) {
            query.eq("workId", searchWorkId);
        }
        if (StringUtils.isNotBlank(searchCountry)) {
            query.eq("country", searchCountry);
        }
        if (StringUtils.isNotBlank(searchPlatform)) {
            query.eq("platform", searchPlatform);
        }
        if (StringUtils.isNotBlank(searchTime)) {
            query.eq("time", searchTime);
        }

        return Result.success(polarityAnalysisMapper.selectList(query));
    }

    // 统计指定极性情感在各个国家的分布情况
    @GetMapping("/getDistribute")
    @ApiOperation(value = "统计指定极性情感在各个国家的分布情况")
    public Result getPolarityDistribute(@RequestParam Integer searchWorkId,
                                           @RequestParam(defaultValue = "积极", required = false) String selectEmotion,
                                           @RequestParam(defaultValue = "6", required = false) Integer offset) {
        return Result.success(polarityAnalysisMapper.findPolarityDis(searchWorkId, selectEmotion, offset));
    }

    // 查询国家列表
    @GetMapping("/getCountries")
    @ApiOperation(value = "查询经过情感极性分析了的国家列表")
    public Result getCountries(@RequestParam(defaultValue = "-1", required = false) Integer workId) {
        return Result.success(polarityAnalysisMapper.selectCountries(workId));
    }

    // 统计不同情感极性的评论数
    @GetMapping("/countDaily")
    @ApiOperation(value = "统计不同情感极性的评论数")
    public Result countDaily(@RequestParam Integer workId,
                                @RequestParam String country,
                                @RequestParam String postTime
    ) {
        QueryWrapper<PolarityAnalysis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workId", workId);
        queryWrapper.eq("country", country);
        queryWrapper.eq("time", postTime);
        List<PolarityAnalysis> polarityAnalyses
                = polarityAnalysisMapper.selectList(queryWrapper);
        PolarityDto polarityDto = new PolarityDto();
        for (PolarityAnalysis polarityAnalysis: polarityAnalyses) {
            polarityDto.positiveAdd(polarityAnalysis.getPositive());
            polarityDto.negativeAdd(polarityAnalysis.getNegative());
            polarityDto.neutralityAdd(polarityAnalysis.getNeutrality());
        }
        return Result.success(polarityDto);
    }

    // 统计指定时间段内情感极性的评论数
    @GetMapping("/countDayInterval")
    @ApiOperation(value = "统计指定时间段内情感极性的评论数")
    public Result countDayInterval(@RequestParam Integer workId,
                                      @RequestParam(required = false, defaultValue = "") String country,
                                      @RequestParam String startTime,
                                      @RequestParam(required = false, defaultValue = "7") Integer dayInterval
    ) {
        MonitorWork monitorWork = monitorWorkMapper.selectById(workId);
        String workName = monitorWork.getName();
        List<Integer> offsets = new ArrayList<>();
        for (int i = 1; i < dayInterval; i++) {
            offsets.add(i);
        }
        PolarityStatisticsDto polarityStatisticsDto = new PolarityStatisticsDto();
        List<PolarityDto> polarityAnalyses
                = polarityAnalysisMapper.selectDayInterval(workId, country, startTime, offsets);
        polarityStatisticsDto.setWorkId(workId);
        polarityStatisticsDto.setWorkName(workName);
        polarityStatisticsDto.setCountry(country);
        polarityStatisticsDto.setStatisticsInfo(polarityAnalyses);
        return Result.success(polarityStatisticsDto);
    }

    @GetMapping("/countYearPolarity")
    @ApiOperation(value = "统计指定一年的极性情感分析结果")
    public Result countYearPolarity(@RequestParam(required = false, defaultValue = "") Integer workId,
                                    @RequestParam(required = false, defaultValue = "") String country,
                                    @RequestParam(required = true) String year) {
        return Result.success(polarityAnalysisService.getPolarityByYear(workId, country, year));
    }

    // 统计最近一年的极性情感分析结果
    @GetMapping("/countMonthInterval")
    @ApiOperation(value = "统计最近一年的极性情感分析结果")
    public Result countMonthInterval(@RequestParam(required = false, defaultValue = "") String searchCountry,
                                      @RequestParam(required = false, defaultValue = "") Integer workId,
                                      @RequestParam(required = false, defaultValue = "12") Integer offset
    ) {
        List<Integer> offsets = new ArrayList<>();
        for (int i = 1; i < offset; i++) {
            offsets.add(i);
        }
        return Result.success(polarityAnalysisMapper.selectMonthInterval(searchCountry, workId, offsets));
    }

    // 根据id删除指定情感极性分析结果
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "根据ID删除指定情感极性分析结果")
    @ApiImplicitParam(name = "ids", value = "情感极性分析ID数组")
    public Result deleteById(@PathVariable Long[] ids) {
        int res = polarityAnalysisMapper.deleteBatchIds(Arrays.asList(ids));
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "该情感极性分析结果已经被删除了");
    }

    // 新增情感极性分析
    @PostMapping("/add")
    @ApiOperation(value = "新增情感极性分析")
    public Result add(@RequestBody PolarityAnalysis polarityAnalysis) {
        int res = polarityAnalysisMapper.insert(polarityAnalysis);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "插入情感极性分析结果失败");
    }

    // 修改情感极性分析结果
    @PutMapping("/update")
    @ApiOperation(value = "修改情感极性分析结果")
    public Result update(@RequestBody PolarityAnalysis polarityAnalysis) {
        int res = polarityAnalysisMapper.updateById(polarityAnalysis);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "修改情感极性分析结果失败");
    }

    /**
     * Excel导出
     *
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出所有情感极性分析结果")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<PolarityAnalysis> all = polarityAnalysisMapper.findAll();
        for (PolarityAnalysis polarityAnalysis : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("情感极性分析ID", polarityAnalysis.getId());
            row.put("所属作品ID", polarityAnalysis.getWorkId());
            row.put("所属作品名", polarityAnalysis.getMonitorWork().getName());
            row.put("所属作品类型", polarityAnalysis.getMonitorWork().getCategory());
            row.put("所属国家", polarityAnalysis.getCountry());
            row.put("所分析评论的发布时间", polarityAnalysis.getTime());
            row.put("所属平台", polarityAnalysis.getPlatform());
            row.put("积极的评论数", polarityAnalysis.getPositive());
            row.put("消极的评论数", polarityAnalysis.getNegative());
            row.put("中立的评论数", polarityAnalysis.getNeutrality());
            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("情感极性分析信息", "UTF-8");
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
    @ApiOperation(value = "导出情感极性分析结果导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("所属作品ID", "");
        row.put("所属国家", "");
        row.put("所分析评论的发布时间", "");
        row.put("所属平台", "");
        row.put("积极的评论数", "");
        row.put("消极的评论数", "");
        row.put("中立的评论数", "");
        list.add(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("情感极性分析信息导入模板", "UTF-8");
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
     * @return
     * @throws IOException
     */
    @PostMapping("/import")
    @ApiOperation(value = "导入情感极性分析结果")
    public Result upload(MultipartFile file) throws IOException, ParseException {
        InputStream inputStream = file.getInputStream();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取excel表中的每一行数据
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<PolarityAnalysis> saveList = new ArrayList<>();
        for (List<Object> row : lists) { // 将数据保存到列表中
            PolarityAnalysis polarityAnalysis = new PolarityAnalysis();
            polarityAnalysis.setWorkId(Integer.valueOf(row.get(0).toString()));
            polarityAnalysis.setCountry(row.get(1).toString());
            polarityAnalysis.setTime(sdf.parse(row.get(2).toString()));
            polarityAnalysis.setPlatform(row.get(3).toString());
            polarityAnalysis.setPositive(Integer.valueOf(row.get(4).toString()));
            polarityAnalysis.setNegative(Integer.valueOf(row.get(5).toString()));
            polarityAnalysis.setNeutrality(Integer.valueOf(row.get(6).toString()));
            saveList.add(polarityAnalysis);
        }
        for (PolarityAnalysis polarityAnalysis : saveList) {
            polarityAnalysisMapper.insert(polarityAnalysis); // 插入数据
        }
        return Result.success();
    }

    @GetMapping("/getMonthAnalysisResult")
    @ApiOperation(value = "查询某月的极性情感分析结果")
    public Result getMonthAnalysisResult(@RequestParam(required = false) Integer workId, @RequestParam(required = false) String country, @RequestParam String selectMonth) {
        PolarityDto polarityDto = polarityAnalysisMapper.selectPolarityByMonth(workId, country, selectMonth);
        return Result.success(polarityDto);
    }
}
