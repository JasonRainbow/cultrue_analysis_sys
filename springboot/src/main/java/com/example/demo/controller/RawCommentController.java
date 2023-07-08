package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.RawComment;
import com.example.demo.mapper.RawCommentMapper;
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

// 监测文化作品信息
@RequestMapping("/api/comment")
@RestController
public class RawCommentController {
    @Autowired
    private RawCommentMapper rawCommentMapper;

    // 查询所有的评论信息
    @GetMapping("/all")
    public Result<?> findAll() {
        List<RawComment> rawComments = rawCommentMapper.selectList(null);
        return Result.success(rawComments);
    }

    // 根据评论id查询评论信息
    @GetMapping("/id/{id}")
    public Result<?> findById(@PathVariable Long id) {
        RawComment rawComment = rawCommentMapper.selectById(id);
        return Result.success(rawComment);
    }

    // 查询所有的国家信息列表
    @GetMapping("/countries")
    public Result<?> findAllCountry() {
        List<String> list = rawCommentMapper.selectAllCountry();
        return Result.success(list);
    }

    // 查询所有的国家信息列表
    @GetMapping("/platforms")
    public Result<?> findAllPlatform() {
        List<String> list = rawCommentMapper.selectAllPlatform();
        return Result.success(list);
    }

    // 带搜索关键词的分页查询评论信息
    @GetMapping("/byPage")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "-1") Integer workId,
                              @RequestParam(required = false, defaultValue = "") String searchContent,
                              @RequestParam(required = false, defaultValue = "") String searchCountry,
                              @RequestParam(required = false, defaultValue = "") String searchPlatform,
                              @RequestParam(required = false, defaultValue = "") String searchTime,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws ParseException {
        LambdaQueryWrapper<RawComment> queryWrapper = Wrappers.<RawComment>lambdaQuery()
                .like(RawComment::getContent, searchContent)
                .like(RawComment::getCountry, searchCountry)
                .like(RawComment::getPlatform, searchPlatform)
                .orderByDesc(RawComment::getLikes);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(searchTime)) {
            queryWrapper.eq(RawComment::getPostTime, sdf.parse(searchTime));
        }
        if (workId != -1) queryWrapper.eq(RawComment::getWorkId, workId);
        return Result.success(rawCommentMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 根据id删除指定评论
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteById(@PathVariable Long id) {
        int res = rawCommentMapper.deleteById(id);
        if (res > 0) {
            Result.success();
        }
        return Result.error("-1", "该评论已经被删除了");
    }

    // 新增评论信息
    @PostMapping("/add")
    public Result<?> add(@RequestBody RawComment rawComment) {
        int res = rawCommentMapper.insert(rawComment);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "插入评论失败");
    }

    // 修改热点文化作品信息
    @PutMapping("/update")
    public Result<?> update(@RequestBody RawComment rawComment) {
        int res = rawCommentMapper.updateById(rawComment);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "修改评论失败");
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

        List<RawComment> all = rawCommentMapper.selectList(null);
        for (RawComment rawComment : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("评论内容", rawComment.getContent());
            row.put("评论点赞数", rawComment.getLikes());
            row.put("评论的情感倾向", rawComment.getSentiment());
            row.put("评论所属国家", rawComment.getCountry());
            row.put("评论所属平台", rawComment.getPlatform());
            row.put("评论发布的时间", rawComment.getPostTime());
            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("作品相关评论信息", "UTF-8");
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
        row.put("评论内容", "");
        row.put("评论的作品ID", "");
        row.put("评论点赞数", "");
        row.put("评论的情感倾向", "");
        row.put("评论所属国家", "");
        row.put("评论所属平台", "");
        row.put("评论发布的时间", "");
        list.add(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("作品相关评论信息导入模板", "UTF-8");
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
        List<RawComment> saveList = new ArrayList<>();
        for (List<Object> row : lists) { // 将数据保存到列表中
            RawComment rawComment = new RawComment();
            rawComment.setContent(row.get(0).toString());
            rawComment.setLikes(Integer.valueOf(row.get(1).toString()));
            rawComment.setWorkId(Integer.valueOf(row.get(2).toString()));
            rawComment.setSentiment(row.get(3).toString());
            rawComment.setCountry(row.get(4).toString());
            rawComment.setPlatform(row.get(5).toString());
            rawComment.setPostTime(sdf.parse(row.get(6).toString()));
            saveList.add(rawComment);
        }
        for (RawComment rawComment : saveList) {
            rawCommentMapper.insert(rawComment); // 插入数据
        }
        return Result.success();
    }
}
