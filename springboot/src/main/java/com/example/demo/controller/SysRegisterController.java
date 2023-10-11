package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.constant.GlobalConstants;
import com.example.demo.entity.User;
import com.example.demo.service.SysRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册控制器
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户注册控制器")
public class SysRegisterController {

    @Autowired
    private SysRegisterService sysRegisterService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册接口")
    public Result register(@RequestBody User user) {
        String msg = sysRegisterService.doRegister(user);
        return msg.equals(GlobalConstants.REGISTER_SUCCESS_MSG) ?
                Result.success("0", msg) : Result.error("-1", msg);
    }

}
