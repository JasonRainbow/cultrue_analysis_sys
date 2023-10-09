package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.constant.GlobalConstants;
import com.example.demo.entity.SysUserRole;
import com.example.demo.entity.User;
import com.example.demo.enums.RoleEnum;
import com.example.demo.mapper.SysUserRoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.SysRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 注册服务接口的实现类
 */
@Service
public class SysRegisterServiceImpl implements SysRegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String doRegister(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String nickName = user.getName();

        if (StringUtils.isBlank(username)) { // 用户名为空
            return "用户名不能为空";
        }

        if (StringUtils.isBlank(password)) { // 用户输入的密码为空或者空字符串
            return "密码不能为空";
        }

        if (StringUtils.isBlank(nickName)) {
            return "用户昵称不能为空";
        }

        // 对用户名和密码的长度进行检测

        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (res != null) {
            return "该用户名已存在";
        }

        User userInfo = User.builder()
                .username(user.getUsername())
                .password(bCryptPasswordEncoder.encode(user.getPassword())) // 加密
                .name(nickName)
                .workUnit(user.getWorkUnit())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();

        try {
            userMapper.insert(userInfo); // 将用户信息插入到用户表中
            sysUserRoleMapper.insert(
                    SysUserRole.builder()
                            .userId(userInfo.getId())
                            .roleId(RoleEnum.USER.getRoleId()).build()
            ); // 分配给用户一个普通用户的角色，不能分配超级管理员的角色
        } catch (Exception ignored) {
            return "注册失败,请联系系统管理人员";
        }

        return GlobalConstants.REGISTER_SUCCESS_MSG;
    }

}
