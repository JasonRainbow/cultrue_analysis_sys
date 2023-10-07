package com.example.demo.service.impl;

import com.example.demo.entity.SysRole;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysRoleService接口的实现
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据用户名查询用户拥有的角色
     * @param username 用户名
     * @return 角色列表
     */
    @Override
    public List<SysRole> getRolesByUsername(String username) {
        return sysRoleMapper.selectRolesByUsername(username);
    }
}
