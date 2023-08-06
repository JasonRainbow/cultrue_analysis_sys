package com.example.demo.service;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.EmailUtil;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class DataCrawlService {
    @Autowired
    private HttpUtils httpUtils; // 发送http请求工具类

    @Autowired
    private EmailUtil emailUtil; // 发送邮件的工具类

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    public boolean crawlComments(Integer workId, String keyword, String uri, String platform) {
        Map<String, String> param = new HashMap<>();
        param.put("workId", workId.toString());
        param.put("keyword", keyword);
        // 开启一个线程用于发送请求
        new Thread(() -> {
            try {
                String res = httpUtils.get_https(uri, param);
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> objectMap = jsonParser.parseMap(res);
                String code = objectMap.get("code").toString(); // 返回响应码
                String workName = monitorWorkMapper.selectById(workId).getName(); // 获取作品名称
                String message = String.format("《%s》在“%s”平台的评论数据已经爬取完毕", workName, platform);
                boolean success = emailUtil.sendEmails(code, workId, message);
                if (success) {
                    monitorWorkMapper.updateCrawlState(workId, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("爬取评论数据失败");
            }
        }).start();

        return true;
    }

    public boolean crawlScores(Integer workId, String keyword, String uri, String platform) {
        Map<String, String> param = new HashMap<>();
        param.put("workId", workId.toString());
        param.put("keyword", keyword);
        // 开启一个线程用于发送请求
        new Thread(() -> {
            try {
                String res = httpUtils.get_https(uri, param);
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> objectMap = jsonParser.parseMap(res);
                String code = objectMap.get("code").toString(); // 返回响应码
                String workName = monitorWorkMapper.selectById(workId).getName(); // 获取作品名称
                String message = String.format("《%s》在“%s”平台的评分数据已经爬取完毕", workName, platform);
                boolean success = emailUtil.sendEmails(code, workId, message);
                /*if (success) {
                    monitorWorkMapper.updateCrawlState(workId, 1);
                }*/
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("爬取评分数据失败");
            }
        }).start();

        return true;
    }
}
