package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.PolarityAnalysis;
import com.example.demo.entity.dto.PolarityDto;
import com.example.demo.entity.dto.PolarityStatisticsDto;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.PolarityAnalysisMapper;
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
public class PolarityAnalysisController {
    @Autowired
    private PolarityAnalysisMapper polarityAnalysisMapper;

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    // 查询所有的情感极性分析结果
    @GetMapping("/all")
    public Result<?> findAll() {
        List<PolarityAnalysis> polarityAnalyses = polarityAnalysisMapper.findAll();
        return Result.success(polarityAnalyses);
    }

    @GetMapping("/id/{id}")
    public Result<?> findById(@PathVariable Long id) {
        PolarityAnalysis polarityAnalysis = polarityAnalysisMapper.selectById(id);
        return Result.success(polarityAnalysis);
    }

    // 分页 搜索查询
    @GetMapping("/byPage")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String searchWorkName,
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

    // 统计不同情感极性的评论数
    @GetMapping("/countDaily")
    public Result<?> countDaily(@RequestParam Integer workId,
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

    // 统计不同情感极性的评论数
    @GetMapping("/countDayInterval")
    public Result<?> countDayInterval(@RequestParam Integer workId,
                                      @RequestParam String country,
                                      @RequestParam String startTime,
                                      @RequestParam Integer dayInterval
    ) {
        MonitorWork monitorWork = monitorWorkMapper.selectById(workId);
        String workName = monitorWork.getName();
        PolarityStatisticsDto polarityStatisticsDto = new PolarityStatisticsDto();
        List<PolarityDto> polarityAnalyses
                = polarityAnalysisMapper.selectDayInterval(workId, country, startTime, dayInterval);
        polarityStatisticsDto.setWorkId(workId);
        polarityStatisticsDto.setWorkName(workName);
        polarityStatisticsDto.setCountry(country);
        polarityStatisticsDto.setStatisticsInfo(polarityAnalyses);
        return Result.success(polarityStatisticsDto);
    }

    // 根据id删除指定情感极性分析结果
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteById(@PathVariable Long id) {
        int res = polarityAnalysisMapper.deleteById(id);
        if (res > 0) {
            Result.success();
        }
        return Result.error("-1", "该情感极性分析结果已经被删除了");
    }

    // 新增情感极性分析
    @PostMapping("/add")
    public Result<?> add(@RequestBody PolarityAnalysis polarityAnalysis) {
        int res = polarityAnalysisMapper.insert(polarityAnalysis);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "插入情感极性分析结果失败");
    }

    // 修改情感极性分析结果
    @PutMapping("/update")
    public Result<?> update(@RequestBody PolarityAnalysis polarityAnalysis) {
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
    @RequestMapping("/export")
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
    @RequestMapping("/importTemplate")
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
    public Result<?> upload(MultipartFile file) throws IOException, ParseException {
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
}
