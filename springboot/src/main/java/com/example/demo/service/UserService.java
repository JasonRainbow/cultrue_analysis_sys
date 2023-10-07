package com.example.demo.service;

import com.example.demo.entity.User;

/**
 * 用户信息相关服务
 */
public interface UserService {

    User getUserByUsername(String username);
}
