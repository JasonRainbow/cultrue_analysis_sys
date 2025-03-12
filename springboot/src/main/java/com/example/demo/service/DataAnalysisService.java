package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.authentication.MyOncePerRequestFilter;
import com.example.demo.entity.SentimentAnalysis;
import com.example.demo.entity.WorkScore;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.SentimentAnalysisMapper;
import com.example.demo.mapper.WorkScoreMapper;
import com.example.demo.utils.EmailUtil;
import com.example.demo.utils.HttpUtils;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(DataAnalysisService.class);

    @Autowired
    private HttpUtils httpUtils; // 发送http请求工具类

    @Autowired
    private EmailUtil emailUtil; // 发送邮件的工具类

    @Value("${remote_host1}")
    private String address1;

    @Value("${remote_host2}")
    private String address2;

    @Value("${remote_host3}")
    private String address3;

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    @Autowired
    private SentimentAnalysisMapper sentimentAnalysisMapper;

    @Autowired
    private WorkScoreMapper workScoreMapper;

    public boolean countWordFreq(Integer workId) {
        Map<String, String> param = new HashMap<>();
        param.put("workId", workId.toString());
        new Thread(()->{
            try {
                String res = httpUtils.get(address1 + "/words_freq_sta", param);
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> objectMap = jsonParser.parseMap(res);
                String code = objectMap.get("code").toString(); // 返回响应码
                String workName = monitorWorkMapper.selectById(workId).getName(); // 获取作品名称
                String message = String.format("《%s》的评论关键词提取和词频统计已经完成", workName);
                boolean success = emailUtil.sendEmails(code, workId, message);
                if (success) {
                    monitorWorkMapper.updateWordFreqState(workId, 1);
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                System.out.println("词频统计失败");
            }
        }).start();
        return true;
    }

    // 情感极性分析
    public boolean polarityAnalysis(Integer workId) {
        Map<String, String> param = new HashMap<>();
        param.put("workId", workId.toString());
        new Thread(()->{
            try {
                String res = httpUtils.get(address2 + "/analyze_polarity", param);
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> objectMap = jsonParser.parseMap(res);
                String code = objectMap.get("code").toString(); // 返回响应码
                String workName = monitorWorkMapper.selectById(workId).getName(); // 获取作品名称
                String message = String.format("《%s》的情感极性分析已经完成", workName);
                boolean success = emailUtil.sendEmails(code, workId, message);
                if (success) {
                    monitorWorkMapper.updatePolarityState(workId, 1);
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                System.out.println("情感极性分析失败");
            }
        }).start();
        return true;
    }

    // 细腻情感分析
    public boolean sentimentAnalysis(Integer workId) {
        Map<String, String> param = new HashMap<>();
        param.put("workId", workId.toString());
        new Thread(()->{
            try {
                String res = httpUtils.get(address2 + "/analyze_sentiment", param);
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> objectMap = jsonParser.parseMap(res);
                String code = objectMap.get("code").toString(); // 返回响应码
                String workName = monitorWorkMapper.selectById(workId).getName(); // 获取作品名称
                String message = String.format("《%s》的细腻情感分析已经完成", workName);
                boolean success = emailUtil.sendEmails(code, workId, message);
                if (success) {
                    monitorWorkMapper.updateSentimentState(workId, 1);
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                System.out.println("细腻情感分析失败");
            }
        }).start();
        return true;
    }


    // 将计算某个影视作品在Facebook、Twitter、Youtube的评分，通过细腻情感的转换
    public boolean generatePlatformScore(int workId, String platform) {
        float score = sentiToScore(workId, platform);
        if (Float.isNaN(score)) {
            return false;
        }
        int res = workScoreMapper.insert(WorkScore.builder()
                .score(score)
                .workId(workId)
                .platform(platform)
                .build());
        return res > 0;
    }

    // 对作品的评论进行主题情感分析，并进行统计
    public boolean analyzeSubjectByWorkId(Integer workId) {
        Map<String, String> param = new HashMap<>();
        param.put("workId", workId.toString());
        new Thread(()->{
            try {
                String res = httpUtils.get(address2 + "/subject_analysis", param);
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> objectMap = jsonParser.parseMap(res);
                String code = objectMap.get("code").toString(); // 返回响应码
                String workName = monitorWorkMapper.selectById(workId).getName(); // 获取作品名称
                String message = String.format("《%s》的主题情感分析完成", workName);
                boolean success = emailUtil.sendEmails(code, workId, message);
                /*if (success) {
                    monitorWorkMapper.updateSentimentState(workId, 1);
                }*/
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                System.out.println("主题情感分析失败");
            }
        }).start();
        return true;
    }

    // 向另一个服务器发送请求，更新推荐列表
    public boolean updateRecommendation() {
        // 创建一个线程去发送http请求
        new Thread(()->{
            try {
                httpUtils.get(address3 + "/recommend", null);
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                System.out.println("更新推荐列表失败");
            }
        }).start();
        return true;
    }

    //根据作品编号和排名进行细腻情感至评分的映射 返回评分
    private float sentiToScore(int workId, String platform) {
        int count = 0;
        int sum = 0;
        QueryWrapper<SentimentAnalysis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workId", workId);
        queryWrapper.eq("platform", platform);
        List<SentimentAnalysis> selectList = sentimentAnalysisMapper.selectList(queryWrapper);
        for (SentimentAnalysis sentimentAnalysis: selectList) {
            //获取各个属性值 创建选课对象
            int happy = sentimentAnalysis.getHappy();
            int amazed = sentimentAnalysis.getAmazed();
            int neutrality = sentimentAnalysis.getNeutrality();
            int sad = sentimentAnalysis.getSad();
            int angry = sentimentAnalysis.getAngry();
            int fear = sentimentAnalysis.getFear();
            count += happy+amazed+neutrality+sad+angry+fear;
            sum += happy*5 + amazed*4 + neutrality*3 + sad*2 + angry;
        }
//        System.out.println("sum:"+sum+" count:"+count+" sum/count:"+(float)sum/count);
        DecimalFormat df = new DecimalFormat("0.0"); // 创建DecimalFormat对象，指定格式为保留一位小数
        String result = df.format((float)sum/count); // 格式化浮点数为字符串
        return Float.parseFloat(result);
    }
}
