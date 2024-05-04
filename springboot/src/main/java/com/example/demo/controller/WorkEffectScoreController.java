package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.WorkInformation;
import com.example.demo.entity.WorkInformationWeight;
import com.example.demo.entity.vo.DoubanData;
import com.example.demo.entity.vo.IMDbData;
import com.example.demo.entity.vo.WorkEffectScoreVo;
import com.example.demo.entity.vo.YoutubeData;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.WorkInformationMapper;
import com.example.demo.mapper.WorkInformationWeightMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.Comparator.comparing;


@RestController
@RequestMapping("/api/workEffectScore")
@Api(tags = "作品传播效果得分控制器")
public class WorkEffectScoreController {
    @Autowired
    private WorkInformationMapper workInformationMapper;
    @Autowired
    private WorkInformationWeightMapper workInformationWeightMapper;

    @Autowired
    MonitorWorkMapper monitorWorkMapper;

    @ApiOperation(value = "根据ID和平台查询文化作品的传播效果")
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
            IMDbData imDbData = workInformationMapper.getIMDbData(platform);

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
        }else if(platform.equals("Youtube")){
            effectScore = getEffectScore(workId, platform);
        } else{
            return Result.success(null);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("workId", workInformation.getWorkId());
        map.put("workName", workInformation.getWorkName());
        map.put("platform", workInformation.getPlatform());
        map.put("effectScore", effectScore);
        return Result.success(map);
    }

    //根据文化作品id查询它的综合评分
    public double getComprehensiveEffectScore(Integer workId){
        double doubanEffectScore = getEffectScore(workId, "豆瓣");
        double IMDbEffectScore = getEffectScore(workId, "IMDb");
        double youtubeEffectScore = getEffectScore(workId, "Youtube");
        if(doubanEffectScore == 0 && IMDbEffectScore == 0 && youtubeEffectScore == 0){
            return 0;
        }
        Integer doubanCommentNum = 0;
        Integer IMDbCommentNum = 0;
        Integer youtubeCommentNum = 0;
        double effectScore = 0.0;

        LambdaQueryWrapper<WorkInformation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        WorkInformation workInformation;
        if(doubanEffectScore == 0){
            doubanCommentNum = 0;
        }else{
            lambdaQueryWrapper.eq(WorkInformation::getWorkId, workId);
            lambdaQueryWrapper.eq(WorkInformation::getPlatform, "豆瓣");
            workInformation = workInformationMapper.selectOne(lambdaQueryWrapper);
            doubanCommentNum = workInformation.getCommentNum();
        }

        if(IMDbEffectScore == 0){
            IMDbCommentNum = 0;
        }else{
            lambdaQueryWrapper.clear();
            lambdaQueryWrapper.eq(WorkInformation::getWorkId, workId);
            lambdaQueryWrapper.eq(WorkInformation::getPlatform, "IMDb");
            workInformation = workInformationMapper.selectOne(lambdaQueryWrapper);
            IMDbCommentNum = workInformation.getCommentNum();
        }

        if(youtubeEffectScore == 0){
            youtubeCommentNum = 0;
        }else{
            lambdaQueryWrapper.clear();
            lambdaQueryWrapper.eq(WorkInformation::getWorkId, workId);
            lambdaQueryWrapper.eq(WorkInformation::getPlatform, "Youtube");
            workInformation = workInformationMapper.selectOne(lambdaQueryWrapper);
            youtubeCommentNum = workInformation.getCommentNum();
        }

        Integer totalCommentNum = doubanCommentNum + IMDbCommentNum + youtubeCommentNum;

        effectScore = doubanEffectScore * ((double)doubanCommentNum/totalCommentNum) +IMDbEffectScore * ((double)IMDbCommentNum/totalCommentNum)
                + youtubeEffectScore * ((double)youtubeCommentNum/totalCommentNum);

        return effectScore;
    }

    @ApiOperation(value = "根据ID查询文化作品的综合传播效果")
    @GetMapping("/getByWorkId")
    private Result getWorkEffectScore(@RequestParam(required = true) Integer workId){
        double effectScore = getComprehensiveEffectScore(workId);
        if(effectScore == 0){
            return Result.success(null);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("workId", workId);
        map.put("effectScore", effectScore);
        return Result.success(map);
    }

   //获取某个作品在某个平台上的传播效果得分
    private double getEffectScore(Integer workId, String platform){
        LambdaQueryWrapper<WorkInformation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkInformation::getWorkId, workId);
        lambdaQueryWrapper.eq(WorkInformation::getPlatform, platform);
        WorkInformation workInformation = workInformationMapper.selectOne(lambdaQueryWrapper);

        if(workInformation == null){
            return 0;
        }

        LambdaQueryWrapper<WorkInformationWeight> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(WorkInformationWeight::getPlatform, platform);
        WorkInformationWeight workInformationWeight = workInformationWeightMapper.selectOne(lambdaQueryWrapper1);

        if(workInformationWeight == null){
            return 0;
        }

        if(platform.equals("豆瓣")){
            DoubanData doubanData = workInformationMapper.getDoubanData(platform);

            doubanData.setNormalizeScore((workInformation.getScore()-doubanData.getMinScore())/(doubanData.getMaxScore()-doubanData.getMinScore())*100);

            double preProcessCommentNum = Math.log10(workInformation.getCommentNum());
            double preProcessMaxCommentNum = Math.log10(doubanData.getMaxCommentNum());
            double preProcessMinCommentNum = Math.log10(doubanData.getMinCommentNum());

            doubanData.setNormalizeCommentNum((preProcessCommentNum - preProcessMinCommentNum)/(preProcessMaxCommentNum - preProcessMinCommentNum)*100);

            double effectScore =doubanData.getNormalizeScore() * workInformationWeight.getScoreWeight() + doubanData.getNormalizeCommentNum() * workInformationWeight.getCommentNumWeight();
            return effectScore;

        }else if(platform.equals("IMDb")){
            IMDbData imDbData = workInformationMapper.getIMDbData(platform);

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

            double effectScore =imDbData.getNormalizeScore() * workInformationWeight.getScoreWeight()
                    + imDbData.getNormalizeCommentNum() * workInformationWeight.getCommentNumWeight()
                    +imDbData.getNormalizeCollection() * workInformationWeight.getCollectionWeight();

            return effectScore;
        }else if(platform.equals("Youtube")){
            YoutubeData youtubeData = workInformationMapper.getYoutubeData(platform);

            double preProcessZanNum = Math.log10(workInformation.getZanNum());
            double preProcessMaxZanNum = Math.log10(youtubeData.getMaxZanNum());
            double preProcessMinZanNum = Math.log10(youtubeData.getMinZanNum());
            double preProcessCommentNum = Math.log10(workInformation.getCommentNum());
            double preProcessMaxCommentNum = Math.log10(youtubeData.getMaxCommentNum());
            double preProcessMinCommentNum = Math.log10(youtubeData.getMinCommentNum());
            double preProcessViewNum = Math.log10(workInformation.getViewNum());
            double preProcessMaxViewNum = Math.log10(youtubeData.getMaxViewNum());
            double preProcessMinViewNum = Math.log10(youtubeData.getMinViewNum());

            youtubeData.setNormalizeZanNum((preProcessZanNum - preProcessMinZanNum)/(preProcessMaxZanNum - preProcessMinZanNum)*100);
            youtubeData.setNormalizeCommentNum((preProcessCommentNum - preProcessMinCommentNum)/(preProcessMaxCommentNum-preProcessMinCommentNum)*100);
            youtubeData.setNormalizeViewNum((preProcessViewNum - preProcessMinViewNum)/(preProcessMaxViewNum-preProcessMinViewNum)*100);

            double effectScore = youtubeData.getNormalizeZanNum() * workInformationWeight.getZanNumWeight() +
                    youtubeData.getNormalizeCommentNum() * workInformationWeight.getCommentNumWeight() +
                    youtubeData.getNormalizeViewNum() * workInformationWeight.getViewNumWeight();
            return effectScore;
        }else{
            return 0;
        }
    }

    @ApiOperation(value = "根据ID查询同类别的文化作品的综合传播效果")
    @GetMapping("/getSameCategoryEffectScoreByWorkId")
    public Result getSameCategoryEffectScore(@RequestParam(required = true) Integer workId){
        MonitorWork work = monitorWorkMapper.selectById(workId);
        if(work == null){
            return Result.error("-1", "出错了，请联系管理员");
        }
        LambdaQueryWrapper<MonitorWork> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MonitorWork::getSubCategory, work.getSubCategory());
//        lambdaQueryWrapper.ne(MonitorWork::getId, workId);
        List<MonitorWork> list = monitorWorkMapper.selectList(lambdaQueryWrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("workId", workId);
        List<WorkEffectScoreVo> sameList = new ArrayList<>();
        for(MonitorWork monitorWork : list){
            double effectScore = getComprehensiveEffectScore(monitorWork.getId());
            if(effectScore != 0){
                WorkEffectScoreVo workEffectScoreVo = new WorkEffectScoreVo(monitorWork.getName(), effectScore);
                sameList.add(workEffectScoreVo);
            }
        }
        sameList.sort(comparing(WorkEffectScoreVo::getEffectScore).reversed());
        if(sameList.size()>10){
            sameList = sameList.subList(0, 10);
        }
        map.put("WorkEffectScoreList", sameList);
        return Result.success(map);
    }
}
