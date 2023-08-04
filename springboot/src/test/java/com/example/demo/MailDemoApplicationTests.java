package com.example.demo;

import com.example.demo.utils.EmailUtil;
import com.example.demo.utils.HttpUtils;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class MailDemoApplicationTests {
    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private HttpUtils httpUtils;

    @Test
    public void testEmailSend() {
//        emailUtil.sendSimpleMail("1728326545@qq.com", "这是一封测试邮件");
        try {
            emailUtil.sendHtmlMail("111", "xxx", "xxx", "xxx");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            System.out.println("发送邮件失败");
        }
    }

    @Test
    public void test2() throws ParseException {
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(sdf.parse("2023/7/1 0:00"));*/
    }

    @Test
    public void test3() {
        String url = "47.115.211.201:5000";
        Map<String, String> param = new HashMap<>();
        param.put("workId", "2");
        try {
            String res = httpUtils.get(url, param);
            JsonParser jsonParser = JsonParserFactory.getJsonParser();
            Map<String, Object> objectMap = jsonParser.parseMap(res);
            System.out.println(objectMap);
            System.out.println(objectMap.get("code").toString());
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("请求失败");
        }
    }

    public static String unicodeToString(String unicode) {
        StringBuffer string = new StringBuffer();
        /* 以 \ u切割 */
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            /* 这里代表将值转为16进制表示，一共有2, 8, 10, 16几种表示 */
            int data = Integer.valueOf(hex[i], 16);
            /* 追加成String */
            string.append((char) data);
        }
        return string.toString();
    }
}
