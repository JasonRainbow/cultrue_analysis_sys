package com.example.demo.config.authentication;

import com.example.demo.common.Result;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器 继承自定义的JSON格式输出类JSONAuthentication输出JSON格式，同时判断是什么异常并做出针对性输出
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result result = null;
        if (e instanceof InternalAuthenticationServiceException) {
            // 用户不存在
            result = Result.error(ResponseStatusEnum.USER_ACCOUNT_NOT_EXIST);
        } else if (e instanceof BadCredentialsException) {
            // 用户名或密码错误，也就是用户名匹配不上密码
            result = Result.error(ResponseStatusEnum.USERNAME_PASSWORD_ERROR);
        } else if (e instanceof DisabledException) {
            // 账号不可用
            result = Result.error(ResponseStatusEnum.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException) {
            // 账号锁定
            result = Result.error(ResponseStatusEnum.USER_ACCOUNT_LOCKED);
        } else if (e instanceof InsufficientAuthenticationException) {
            // 认证失败
            result = Result.error(ResponseStatusEnum.AUTHENTICATE_FAIL);
        } else {
            // 其他错误
            result = Result.error(ResponseStatusEnum.USER_NOT_LOGIN);
        }

        // 打印错误
        logger.error(String.valueOf(e));

        // 输出
        JsonUtil.writeJson(httpServletRequest, httpServletResponse, result);
    }
}
