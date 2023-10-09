package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.dto.LoginDto;

public interface LoginService {

    /**
     * 处理登录
     * @param loginDto 登录用户对象
     * @return
     */
    Result doLogin(LoginDto loginDto, boolean adminLogin);

    /**
     * 处理用户退出登录
     * @return
     */
    Result doLogout();
}
