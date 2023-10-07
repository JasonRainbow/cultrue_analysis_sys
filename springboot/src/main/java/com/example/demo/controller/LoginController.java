package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.dto.LoginDto;
import com.example.demo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * 这个控制器没有用到 ”/login“ 这个url，因为它是SpringSecurity的UsernamePasswordAuthenticationFilter拦截器自己设定的
 * 同时它还设置了必须使用POST方式才能进行登录
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录控制器")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录的接口")
    public Result login(@RequestBody LoginDto loginDto) {
        return loginService.doLogin(loginDto);
    }

    @RequestMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录的接口")
    public Result logout() {
        return loginService.doLogout();
    }
}
