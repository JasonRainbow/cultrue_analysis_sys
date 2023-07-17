package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.dto.CommentPlatformDto;
import com.example.demo.entity.MonitorWork;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.RawCommentMapper;
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
import java.util.*;

// 监测文化作品信息
@RequestMapping("/api/monitor-work")
@RestController
@Api(tags = "监测作品控制器")
public class MonitorWorkController {
    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    @Autowired
    private RawCommentMapper rawCommentMapper;

    // 查询所有的监测作品信息
    @GetMapping("/all")
    @ApiOperation(value = "查询所有监测作品信息")
    public Result<?> findAll() {
        List<MonitorWork> monitorWorks = monitorWorkMapper.selectList(null);
        return Result.success(monitorWorks);
    }

    // 根据作品id查询指定的文化作品
    @GetMapping("/id/{id}")
    @ApiOperation(value = "根据ID查询指定文化作品信息")
    public Result<?> findById(@PathVariable Long id) {
        MonitorWork monitorWork = monitorWorkMapper.selectById(id);
        return Result.success(monitorWork);
    }

    // 分页 搜索查询
    @GetMapping("/byPage")
    @ApiOperation(value = "分页查询监测作品信息")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String searchName,
                              @RequestParam(required = false, defaultValue = "") String searchCategory,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<MonitorWork> queryWrapper = Wrappers.<MonitorWork>lambdaQuery()
                .like(MonitorWork::getName, searchName)
                .like(MonitorWork::getCategory, searchCategory)
                .orderByAsc(MonitorWork::getId);
        return Result.success(monitorWorkMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 根据id删除指定监测作品
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "根据ID删除指定监测作品")
    @ApiImplicitParam(name = "ids", value = "监测作品ID数组")
    public Result<?> deleteById(@PathVariable Long[] ids) {
        int res = monitorWorkMapper.deleteBatchIds(Arrays.asList(ids));
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "该监测作品已经被删除了");
    }

    // 新增监测文化作品
    @PostMapping("/add")
    @ApiOperation(value = "新增监测作品")
    public Result<?> add(@RequestBody MonitorWork monitorWork) {
        monitorWork.setCreateTime(new Date());
        int res = monitorWorkMapper.insert(monitorWork);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "插入监测作品失败");
    }

    // 修改监测作品信息
    @PutMapping("/update")
    @ApiOperation(value = "修改监测作品信息")
    public Result<?> update(@RequestBody MonitorWork monitorWork) {
        int res = monitorWorkMapper.updateById(monitorWork);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "修改监测文化作品失败");
    }

    // 通过用户的id获取用户申请的监测作品信息
    @GetMapping("/byUserId")
    @ApiOperation(value = "根据用户ID查询用户申请的监测作品信息")
    public Result<?> findAllByUserId(@RequestParam(required = false, defaultValue = "-1") Integer userId) {
        List<MonitorWork> monitorWorks = monitorWorkMapper.selectByUserId(userId);
        return Result.success(monitorWorks);
    }

    // 获取指定文化作品的平台来源统计信息
    @GetMapping("/countPlatform")
    @ApiOperation(value = "根据作品ID查询平台来源统计信息")
    public Result<?> countPlatform(@RequestParam Integer workId) {
        List<CommentPlatformDto> platformDtos = rawCommentMapper.countPlatformByWorkId(workId);
        return Result.success(platformDtos);
    }

    /**
     * Excel导出
     *
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出所有监测作品信息")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<MonitorWork> all = monitorWorkMapper.selectList(null);
        for (MonitorWork monitorWork : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("热点文化作品名称", monitorWork.getName());
            row.put("作品类型", monitorWork.getName());
            row.put("作品监测创建时间", monitorWork.getCreateTime());
            row.put("作品监测完成时间", monitorWork.getEndTime());
            row.put("是否完成评论数据爬取", monitorWork.getCrawlOk());
            row.put("是否完成情感分析", monitorWork.getSentimentOk());
            row.put("是否完成情感极性分析", monitorWork.getPolarityOk());
            row.put("是否完成词云图分析", monitorWork.getWordCloudOk());
            row.put("是否完成语义网络分析", monitorWork.getGramNetOk());
            row.put("是否全部完成", monitorWork.getAllDone());
            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("监测文化作品信息", "UTF-8");
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
    @ApiOperation(value = "导出监测作品信息导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("热点文化作品名称", "");
        row.put("作品类型", "");
        list.add(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("监测文化作品信息导入模板", "UTF-8");
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
    @ApiOperation(value = "导入监测作品信息")
    public Result<?> upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        // 获取excel表中的每一行数据
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<MonitorWork> saveList = new ArrayList<>();
        for (List<Object> row : lists) { // 将数据保存到列表中
            MonitorWork monitorWork = new MonitorWork();
            monitorWork.setName(row.get(0).toString());
            monitorWork.setCategory(row.get(1).toString());
            saveList.add(monitorWork);
        }
        for (MonitorWork monitorWork : saveList) {
            monitorWorkMapper.insert(monitorWork); // 插入数据
        }
        return Result.success();
    }
}
