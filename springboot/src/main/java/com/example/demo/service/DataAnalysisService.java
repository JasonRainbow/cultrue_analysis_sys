package com.example.demo.service;

import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.utils.EmailUtil;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataAnalysisService {
    @Autowired
    private HttpUtils httpUtils; // 发送http请求工具类

    @Autowired
    private EmailUtil emailUtil; // 发送邮件的工具类

    @Value("${remote_host1}")
    private String address1;

    @Value("${remote_host2}")
    private String address2;

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

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
                e.printStackTrace();
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
                String res = httpUtils.get_https(address2 + "/analyze_polarity", param);
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
                e.printStackTrace();
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
                String res = httpUtils.get_https(address2 + "/analyze_sentiment", param);
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
                e.printStackTrace();
                System.out.println("细腻情感分析失败");
            }
        }).start();
        return true;
    }


    // 将计算某个影视作品在Facebook、Twitter、Youtube的评分，通过细腻情感的转换
    public boolean generatePlatformScore() {

        return true;
    }
}
