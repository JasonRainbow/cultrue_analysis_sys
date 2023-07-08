package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.enums.RoleEnum;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class TokenUtils {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    private static UserMapper staticUserMapper;

    private static AdminMapper staticAdminMapper;

    @PostConstruct
    public void init() {
        staticUserMapper = userMapper;
        staticAdminMapper = adminMapper;
    }

    /**
     * 生成普通用户的token
     * @param user 用户
     * @return token
     */
    public static String genToken(User user) {
        return JWT.create()
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
                .withAudience(user.getId().toString(), RoleEnum.USER.getLabel())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * 生成管理员的token
     * @param admin 管理员
     * @return token
     */
    public static String genToken(Admin admin) {
        return JWT.create()
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
                .withAudience(admin.getId().toString(), RoleEnum.ADMIN.getLabel())
                .sign(Algorithm.HMAC256(admin.getPassword()));
    }

    /**
     * 获取token中的用户信息
     * @return 用户
     */
    public static User getLoginUser() {
        try {
            return staticUserMapper.selectById(getId());
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }

    public static Admin getLoginAdmin() {
        try {
            return staticAdminMapper.selectById(getId());
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }

    /**
     * 获取请求头中的token
     * @return token字符串
     */
    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return request.getHeader("token"); // 获取请求头中的token
    }

    public static Integer getId() {
        String token = getToken();
        String aud = JWT.decode(token).getAudience().get(0); // 解析token得到用户id
        return Integer.valueOf(aud);
    }

    public static String getUserType() {
        String token = getToken();
        return JWT.decode(token).getAudience().get(1);
    }

    /**
     * 判断密码是否相同
     * @param rawPassword 真实的密码
     * @param encodedPassword 加密后的密码
     * @return 是否相同
     */
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
