package com.example.demo.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.DemoApplication;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class EmailUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserMapper userMapper;

    // 获取yml配置的发送者邮箱
    @Value("${spring.mail.username}")
    private String mainUserName;

    //发送人昵称
    @Value("${spring.mail.nickname}")
    private String nickname;

    private final String subject = "文化作品传播效果分析完成通知";

    /**
     * 普通邮件发送
     */
    public void sendSimpleMail(String target, String content) {
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件发送者，昵称+<邮箱地址>
        message.setFrom(nickname+'<'+mainUserName+'>');
        // 设置邮件接收者，可以有多个接收者，多个接受者参数需要数组形式
        message.setTo(target);
        /*// 设置邮件抄送人，可以有多个抄送人
        message.setCc("12****32*qq.com");
        // 设置隐秘抄送人，可以有多个
        message.setBcc("7******9@qq.com");*/
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText(content);
        // 发送邮件
        javaMailSender.send(message);
    }

    public void sendHtmlMail(String target, String username, String name, String content)
            throws IOException, TemplateException {
        // 首先配置 FreeMarker 模版位置
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        // MailDemoApplication是启动类类名
        ClassLoader loader = DemoApplication.class.getClassLoader();
        configuration.setClassLoaderForTemplateLoading(loader, "templates");
        // 配置模版文件
        Template template = configuration.getTemplate("mail.html");
        // 渲染模版
        StringWriter mail = new StringWriter();
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("name", name);
        map.put("content", content);
        template.process(map, mail);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(nickname+'<'+mainUserName+'>');
            messageHelper.setTo(target);
            message.setSubject(subject);
            messageHelper.setText(mail.toString(), true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    public boolean sendEmails(String code, Integer workId, String message) {
        boolean success = false;
        if ("0".equals(code)) {
            List<User> emailList = userMapper.selectEmailsByWorkId(workId);
            for (User user: emailList) {
                if (StringUtils.isNotBlank(user.getEmail())) {
                    try {
                        sendHtmlMail(user.getEmail(),
                                user.getUsername(), user.getName(), message);
                    } catch (Exception e) {
                        System.out.println("发送邮件失败");
                    }
                }
            }
            success = true;
        }
        return success;
    }
}
