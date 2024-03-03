package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

/**
 * 注册服务
 */
public interface SysRegisterService {

    /**
     * 处理注册
     * @param user 用户对象
     * @return 提示信息
     */
    String doRegister(User user);

}
