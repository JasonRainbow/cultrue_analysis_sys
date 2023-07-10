package com.example.demo.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.enums.RoleEnum;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /*String ip = request.getRemoteAddr();
        System.out.println("访问客户端的ip地址为：" + ip);
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        System.out.println("Client IP address: " + ipAddress.split(",")[0]);*/
        /*String uri = request.getRequestURI();
        System.out.println("request: " + uri);
        String token = null;
        try {
            token = TokenUtils.getToken(); // 获取token
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("401", "未获取到token, 请重新登录");
        }
        if (StrUtil.isBlank(token)) {
            throw new CustomException("401", "未获取到token, 请重新登录");
        }
        String userType = TokenUtils.getUserType();
//        System.out.println(userType);
        if (RoleEnum.USER.getLabel().equals(userType)) { // 普通的用户
            User loginUser = TokenUtils.getLoginUser();
            if (loginUser == null) {
                throw new CustomException("401", "token不合法");
            }
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(loginUser.getPassword())).build();
            try {
                jwtVerifier.verify(token);
            } catch (Exception e) {
                throw new CustomException("401", "token不合法");
            }
        } else if (RoleEnum.ADMIN.getLabel().equals(userType)) { // 管理员用户
            Admin loginAdmin = TokenUtils.getLoginAdmin();
            if (loginAdmin == null) {
                throw new CustomException("401", "token不合法");
            }
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(loginAdmin.getPassword())).build();
            try {
                jwtVerifier.verify(token);
            } catch (Exception e) {
                throw new CustomException("401", "token不合法");
            }
        } else {
            throw new CustomException("401", "token不合法");
        }*/

        return true;
    }
}
