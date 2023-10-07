package com.example.demo.entity.dto;

import lombok.Data;

/**
 * 登录用户对象
 */
@Data
public class LoginDto {

    // 用户名
    String username;

    // 密码
    String password;
}
