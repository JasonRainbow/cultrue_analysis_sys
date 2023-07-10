package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.MonitorRequest;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.User;
import com.example.demo.mapper.MonitorRequestMapper;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

// 监测请求信息
@RequestMapping("/api/monitor-request")
@RestController
public class MonitorRequestController {
    @Autowired
    private MonitorRequestMapper monitorRequestMapper;

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    @Autowired
    private UserMapper userMapper;

    // 查询所有的监测请求信息
    @GetMapping("/all")
    public Result<?> findAll() {
        List<MonitorRequest> monitorRequests = monitorRequestMapper.selectAll("", "");
        return Result.success(monitorRequests);
    }

    // 根据监测请求id查询监测请求信息
    @GetMapping("/id/{id}")
    public Result<?> findById(@PathVariable Long id) {
        MonitorRequest monitorRequest = monitorRequestMapper.selectById(id);
        return Result.success(monitorRequest);
    }

    // 根据用户id查询监测请求信息
    @GetMapping("/byUserId")
    public Result<?> findByUserId(@RequestParam(required = true) Integer userId,
                                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize
                                  ) {
        Page<MonitorRequest> byUserId = monitorRequestMapper
                .selectByUserId(new Page<>(pageNum, pageSize), userId);
        return Result.success(byUserId);
    }

    // 分页 搜索查询 搜索关键词：用户名、作品名
    @GetMapping("/byPage")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String searchUsername,
                              @RequestParam(required = false, defaultValue = "") String searchWorkName,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<MonitorRequest> requestPage = monitorRequestMapper
                .findPage(new Page<>(pageNum, pageSize), searchUsername, searchWorkName);
        return Result.success(requestPage);
    }

    // 根据id删除指定监测请求
    @DeleteMapping("/delete/{ids}")
    public Result<?> deleteById(@PathVariable Long[] ids) {
        int res = monitorRequestMapper.deleteBatchIds(Arrays.asList(ids));
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "该监测请求已经被删除了");
    }

    // 新增监测请求  首先把作品插入监测作品表（如果该作品原来不在监测作品表中）再插入监测请求表
    @PostMapping("/add")
    public Result<?> add(@RequestBody MonitorRequest monitorRequest) {
        QueryWrapper<User> query1 = new QueryWrapper<>();
        query1.eq("username", monitorRequest.getUser().getUsername());
        List<User> users = userMapper.selectList(query1);
        if (users == null || users.size() < 1) {
            return Result.error("-1", "不存在的用户名");
        }
        monitorRequest.setUserId(users.get(0).getId()); // 获取用户的id
        MonitorWork monitorWork = monitorRequest.getMonitorWork();
        String workName = monitorWork.getName();
        LambdaQueryWrapper<MonitorWork> wrapper = Wrappers.<MonitorWork>lambdaQuery()
                .like(MonitorWork::getName, workName);
        List<MonitorWork> monitorWorks = monitorWorkMapper.selectList(wrapper);
        if (monitorWorks.size() == 0) { // 请求的监测作品不在监测作品表中，需要把该监测作品先插入监测作品表
            monitorWork.setCreateTime(new Date()); // 监测作品的检测开始时间先设置为当前时间
            monitorWorkMapper.insert(monitorWork); // 插入监测作品
            MonitorWork newWork = monitorWorkMapper.selectList(wrapper).get(0);
            monitorRequest.setWorkId(newWork.getId());
        } else {
            monitorRequest.setWorkId(monitorWorks.get(0).getId());
        }
        monitorRequest.setCreateTime(new Date()); // 请求时间设置为当前时间
        int res = monitorRequestMapper.insert(monitorRequest);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "新增监测请求失败");
    }

    // 修改监测请求信息
    @PutMapping("/update")
    public Result<?> update(@RequestBody MonitorRequest monitorRequest) {
        int res = monitorRequestMapper.updateById(monitorRequest);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "修改监测请求失败");
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

        List<MonitorRequest> all = monitorRequestMapper.selectAll("", "");
        for (MonitorRequest monitorRequest : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("监测请求ID", monitorRequest.getId());
            row.put("用户ID", monitorRequest.getUserId());
            row.put("用户名", monitorRequest.getUser().getUsername());
            row.put("监测作品ID", monitorRequest.getWorkId());
            row.put("监测作品名", monitorRequest.getMonitorWork().getName());
            row.put("监测请求的创建时间", monitorRequest.getCreateTime());
            row.put("是否通过审核", monitorRequest.getChecked());
            row.put("是否完成监测", monitorRequest.getComplete());
            row.put("请求作品监测的完成时间", monitorRequest.getMonitorWork().getEndTime());
            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("文化作品的监测请求信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }
}
