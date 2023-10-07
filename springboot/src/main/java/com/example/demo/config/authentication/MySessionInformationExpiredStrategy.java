package com.example.demo.config.authentication;

import com.example.demo.common.Result;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.utils.JsonUtil;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义异地登录、账号下线时的处理逻辑
 */
@Component
public class MySessionInformationExpiredStrategy implements org.springframework.security.web.session.SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        HttpServletRequest request = sessionInformationExpiredEvent.getRequest();
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        Result result = Result.error(ResponseStatusEnum.USER_ACCOUNT_LOGIN_IN_OTHER_PLACE.getCode(),
                ResponseStatusEnum.USER_ACCOUNT_LOGIN_IN_OTHER_PLACE.getMsg());
        JsonUtil.writeJson(request, response, result);
    }
}
