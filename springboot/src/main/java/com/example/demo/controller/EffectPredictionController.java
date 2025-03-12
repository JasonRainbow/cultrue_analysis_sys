package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.EffectPredictionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/effect-prediction")
@Api(tags = "传播效果预测控制器")
public class EffectPredictionController {

    @Autowired
    private EffectPredictionService effectPredictionService;

    @GetMapping("/findByWorkIdAndYear")
    @ApiOperation(value = "根据作品ID和年份查询预测信息")
    public Result getByWorkIdAndYear(@RequestParam Integer workId,
                                     @RequestParam(required = false, defaultValue = "-1") Integer year) {
        return Result.success(effectPredictionService.findByWorkIdAndYear(workId, year));
    }

}
