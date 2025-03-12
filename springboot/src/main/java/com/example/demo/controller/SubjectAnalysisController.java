package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.CommentSubject;
import com.example.demo.entity.SubjectAnalysis;
import com.example.demo.mapper.CommentSubjectMapper;
import com.example.demo.mapper.SubjectAnalysisMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subject-analysis")
@Api(tags = "作品评论主题分析控制器")
public class SubjectAnalysisController {
    @Autowired
    private SubjectAnalysisMapper subjectAnalysisMapper;

    @Autowired
    private CommentSubjectMapper commentSubjectMapper;

    @GetMapping("/polarityByWorkIdAndSubject")
    @ApiOperation(value = "查询作品的指定主题情感倾向")
    public Result findByWorkIdAndSubject(@RequestParam Integer workId,
                                            @RequestParam String subject) {
        SubjectAnalysis analysis = subjectAnalysisMapper
                .selectByWorkIdAndSubject(workId, subject);
        if (analysis == null) return Result.success();
        List<Map<String, Object>> resList = new ArrayList<>();
        Map<String, Object> resData1 = new HashMap<>();
        resData1.put("name", "积极");
        resData1.put("value", analysis.getPositive());
        resList.add(resData1);
        Map<String, Object> resData2 = new HashMap<>();
        resData2.put("name", "消极");
        resData2.put("value", analysis.getNegative());
        resList.add(resData2);
        Map<String, Object> resData3 = new HashMap<>();
        resData3.put("name", "中立");
        resData3.put("value", analysis.getNeutrality());
        resList.add(resData3);
        return Result.success(resList);
    }

    @GetMapping("/getSubjectsByWorkId")
    @ApiOperation(value = "查询作品评论包含的主题")
    public Result getSubjects(@RequestParam Integer workId) {
        List<String> subjects = subjectAnalysisMapper.selectSubjectsByWorkId(workId);
        return Result.success(subjects);
    }

    @GetMapping("/getAllByWorkId")
    @ApiOperation(value = "查询作品的评论主题分布")
    public Result getAllByWorkId(@RequestParam Integer workId) {
        List<String> subjects = subjectAnalysisMapper.selectSubjectsByWorkId(workId);
        List<SubjectAnalysis> res = new ArrayList<>();

        for (String subject: subjects) {
            res.add(subjectAnalysisMapper.selectByWorkIdAndSubject(workId, subject));
        }

        return Result.success(res);
    }

    @GetMapping("/getCommentSubjectsByWorkId")
    @ApiOperation(value = "查询作品的评论主题属性")
    public Result getCommentSubjects(@RequestParam Integer workId,
                                     @RequestParam(defaultValue = "1", required = false) Integer pageNum,
                                     @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        LambdaQueryWrapper<CommentSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentSubject::getWorkId, workId);

        return Result.success(commentSubjectMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }
}
