package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.DataAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@Api(tags = "数据分析控制器")
public class DataAnalysisController {
    @Autowired
    private DataAnalysisService dataAnalysisService;

    @GetMapping("/count-word-freq")
    @ApiOperation(value = "对作品的评论进行词频统计")
    public Result countWordFreq(@RequestParam Integer workId) {
        boolean success = dataAnalysisService.countWordFreq(workId);
        if (success) {
            return Result.success();
        }
        return Result.error("-1", "词频统计失败，请稍后重试");
    }

    @GetMapping("/analyze-polarity")
    @ApiOperation(value = "对作品的评论进行情感极性分析")
    public Result analyzePolarity(@RequestParam Integer workId) {
        boolean success = dataAnalysisService.polarityAnalysis(workId);
        if (success) {
            return Result.success();
        }
        return Result.error("-1", "情感极性分析失败，请稍后重试");
    }

    @GetMapping("/analyze-sentiment")
    @ApiOperation(value = "对作品的评论进行细腻情感分析")
    public Result analyzeSentiment(@RequestParam Integer workId) {
        boolean success = dataAnalysisService.sentimentAnalysis(workId);
        if (success) {
            return Result.success();
        }
        return Result.error("-1", "细腻情感分析失败，请稍后重试");
    }

    @GetMapping("/analyze-subject")
    @ApiOperation(value = "对作品的评论进行主题情感分析")
    public Result analyzeSubjectPolarity(@RequestParam Integer workId) {
        boolean success = dataAnalysisService.analyzeSubjectByWorkId(workId);
        if (success) {
            return Result.success();
        }
        return Result.error("-1", "主题情感分析失败，请稍后重试");
    }
}
