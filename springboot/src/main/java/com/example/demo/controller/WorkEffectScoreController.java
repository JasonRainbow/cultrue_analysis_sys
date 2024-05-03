package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.WorkInformation;
import com.example.demo.entity.WorkInformationWeight;
import com.example.demo.entity.vo.DoubanData;
import com.example.demo.entity.vo.IMDbData;
import com.example.demo.mapper.WorkInformationMapper;
import com.example.demo.mapper.WorkInformationWeightMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/workEffectScore")
@Api(tags = "作品传播效果得分控制器")
public class WorkEffectScoreController {
    @Autowired
    private WorkInformationMapper workInformationMapper;
    @Autowired
    private WorkInformationWeightMapper workInformationWeightMapper;

    @GetMapping("/findByWorkIdAndPlatform")
    public Result getWorkEffectScore(@RequestParam(required = true) Integer workId, @RequestParam(required = true) String platform) {
        LambdaQueryWrapper<WorkInformation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkInformation::getWorkId, workId);
        lambdaQueryWrapper.eq(WorkInformation::getPlatform, platform);
        WorkInformation workInformation = workInformationMapper.selectOne(lambdaQueryWrapper);

        if(workInformation == null){
            return Result.success(null);
        }

        LambdaQueryWrapper<WorkInformationWeight> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(WorkInformationWeight::getPlatform, platform);
        WorkInformationWeight workInformationWeight = workInformationWeightMapper.selectOne(lambdaQueryWrapper1);

        if(workInformationWeight == null){
            return Result.success(null);
        }

        double effectScore = 0.0;

        if(platform.equals("豆瓣")){
            DoubanData doubanData = workInformationMapper.getDoubanData(platform);

            doubanData.setNormalizeScore((workInformation.getScore()-doubanData.getMinScore())/(doubanData.getMaxScore()-doubanData.getMinScore())*100);

            double preProcessCommentNum = Math.log10(workInformation.getCommentNum());
            double preProcessMaxCommentNum = Math.log10(doubanData.getMaxCommentNum());
            double preProcessMinCommentNum = Math.log10(doubanData.getMinCommentNum());

            doubanData.setNormalizeCommentNum((preProcessCommentNum - preProcessMinCommentNum)/(preProcessMaxCommentNum - preProcessMinCommentNum)*100);

            effectScore =doubanData.getNormalizeScore() * workInformationWeight.getScoreWeight() + doubanData.getNormalizeCommentNum() * workInformationWeight.getCommentNumWeight();
        }else if(platform.equals("IMDb")){
            IMDbData imDbData = workInformationWeightMapper.getIMDbData(platform);

            double data = (workInformation.getScore()-imDbData.getMinScore())/(imDbData.getMaxScore()-imDbData.getMinScore())*100;
            imDbData.setNormalizeScore(data);

            double preProcessCommentNum = Math.log10(workInformation.getCommentNum());
            double preProcessMaxCommentNum = Math.log10(imDbData.getMaxCommentNum());
            double preProcessMinCommentNum = Math.log10(imDbData.getMinCommentNum());
            double preProcessCollection = Math.log10(workInformation.getCollection());
            double preProcessMaxCollection = Math.log10(imDbData.getMaxCollection());
            double preProcessMinCollection = Math.log10(imDbData.getMinCollection());

            imDbData.setNormalizeCommentNum((preProcessCommentNum - preProcessMinCommentNum)/(preProcessMaxCommentNum - preProcessMinCommentNum)*100);
            imDbData.setNormalizeCollection((preProcessCollection - preProcessMinCollection)/(preProcessMaxCollection - preProcessMinCollection)*100);

            effectScore =imDbData.getNormalizeScore() * workInformationWeight.getScoreWeight()
                    + imDbData.getNormalizeCommentNum() * workInformationWeight.getCommentNumWeight()
                    +imDbData.getNormalizeCollection() * workInformationWeight.getCollectionWeight();
        }else{
            return Result.success(null);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("workId", workInformation.getWorkId());
        map.put("workName", workInformation.getWorkName());
        map.put("platform", workInformation.getPlatform());
        map.put("effectScore", effectScore);
        return Result.success(map);
    }

   //获取某个作品在某个平台上的传播效果得分
    private Double getEffectScore(Integer workId, String platform){
        LambdaQueryWrapper<WorkInformation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkInformation::getWorkId, workId);
        lambdaQueryWrapper.eq(WorkInformation::getPlatform, platform);
        WorkInformation workInformation = workInformationMapper.selectOne(lambdaQueryWrapper);

        if(workInformation == null){
            return null;
        }

        LambdaQueryWrapper<WorkInformationWeight> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(WorkInformationWeight::getPlatform, platform);
        WorkInformationWeight workInformationWeight = workInformationWeightMapper.selectOne(lambdaQueryWrapper1);

        if(workInformationWeight == null){
            return null;
        }

        if(platform.equals("豆瓣")){
            DoubanData doubanData = workInformationMapper.getDoubanData(platform);

            doubanData.setNormalizeScore((workInformation.getScore()-doubanData.getMinScore())/(doubanData.getMaxScore()-doubanData.getMinScore())*100);

            double preProcessCommentNum = Math.log10(workInformation.getCommentNum());
            double preProcessMaxCommentNum = Math.log10(doubanData.getMaxCommentNum());
            double preProcessMinCommentNum = Math.log10(doubanData.getMinCommentNum());

            doubanData.setNormalizeCommentNum((preProcessCommentNum - preProcessMinCommentNum)/(preProcessMaxCommentNum - preProcessMinCommentNum)*100);

            Double effectScore =doubanData.getNormalizeScore() * workInformationWeight.getScoreWeight() + doubanData.getNormalizeCommentNum() * workInformationWeight.getCommentNumWeight();
            return effectScore;

        }else if(platform.equals("IMDb")){
            IMDbData imDbData = workInformationWeightMapper.getIMDbData(platform);

            double data = (workInformation.getScore()-imDbData.getMinScore())/(imDbData.getMaxScore()-imDbData.getMinScore())*100;
            imDbData.setNormalizeScore(data);

            double preProcessCommentNum = Math.log10(workInformation.getCommentNum());
            double preProcessMaxCommentNum = Math.log10(imDbData.getMaxCommentNum());
            double preProcessMinCommentNum = Math.log10(imDbData.getMinCommentNum());
            double preProcessCollection = Math.log10(workInformation.getCollection());
            double preProcessMaxCollection = Math.log10(imDbData.getMaxCollection());
            double preProcessMinCollection = Math.log10(imDbData.getMinCollection());

            imDbData.setNormalizeCommentNum((preProcessCommentNum - preProcessMinCommentNum)/(preProcessMaxCommentNum - preProcessMinCommentNum)*100);
            imDbData.setNormalizeCollection((preProcessCollection - preProcessMinCollection)/(preProcessMaxCollection - preProcessMinCollection)*100);

            Double effectScore =imDbData.getNormalizeScore() * workInformationWeight.getScoreWeight()
                    + imDbData.getNormalizeCommentNum() * workInformationWeight.getCommentNumWeight()
                    +imDbData.getNormalizeCollection() * workInformationWeight.getCollectionWeight();

            return effectScore;
        }else{
            return null;
        }
    }
}
