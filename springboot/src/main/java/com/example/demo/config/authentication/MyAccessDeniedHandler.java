package com.example.demo.config.authentication;

import com.example.demo.common.Result;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.utils.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理器
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 用户权限不足
        Result result = Result.error(ResponseStatusEnum.USER_NO_PERMISSIONS);

        // 输出
        JsonUtil.writeJson(httpServletRequest, httpServletResponse, result);
    }
}
