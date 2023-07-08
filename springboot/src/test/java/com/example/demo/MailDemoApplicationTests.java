package com.example.demo;

import com.example.demo.utils.EmailUtil;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class MailDemoApplicationTests {
    @Autowired
    private EmailUtil emailUtil;

    @Test
    public void testEmailSend() {
//        emailUtil.sendSimpleMail("1728326545@qq.com", "这是一封测试邮件");
        try {
            emailUtil.sendHtmlMail("3149125715@qq.com", "张凯芹");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(sdf.parse("2023/7/1 0:00"));
    }
}
