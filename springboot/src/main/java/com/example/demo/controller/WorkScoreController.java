package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.WorkScore;
import com.example.demo.mapper.WorkScoreMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/api/work-score")
@Api(tags = "作品得分控制器")
public class WorkScoreController {
    @Autowired
    private WorkScoreMapper workScoreMapper;

    @GetMapping("/findByWorkId")
    @ApiOperation(value = "查询作品的得分情况")
    public Result findByWorkId(@RequestParam Integer workId) {
        List<WorkScore> workScores = workScoreMapper.countScoreByWorkId(workId);
        if (workScores.size() == 0) return Result.success();
        float meanScore = 0;
        for (WorkScore workScore: workScores) {
            meanScore += workScore.getScore();
        }
        meanScore /= workScores.size();
        meanScore = Float.parseFloat(new DecimalFormat("0.0").format(meanScore));
        workScores.add(WorkScore.builder().score(meanScore).platform("平均").build());
        return Result.success(workScores);
    }
}
