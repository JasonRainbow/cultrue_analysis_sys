package com.example.demo.service;

import com.example.demo.entity.SysRole;

import java.util.List;

/**
 * 角色相关服务
 */
public interface SysRoleService {

    /**
     * 根据用户名查找用户拥有的角色
     * @param username 用户名
     * @return 角色列表
     */
    List<SysRole> getRolesByUsername(String username);

}
