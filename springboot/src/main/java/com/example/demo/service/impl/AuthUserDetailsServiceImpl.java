package com.example.demo.service.impl;

import com.example.demo.entity.SysRole;
import com.example.demo.entity.User;
import com.example.demo.entity.model.LoginUser;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.exception.CustomException;
import com.example.demo.service.SysRoleService;
import com.example.demo.service.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现UserDetailsService，这个接口是SpringSecurity提供的
 */
@Service(value = "userDetailsService")
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthUserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 通过账号查找用户、角色信息
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException 用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            // 用户名不存在
            throw new CustomException(ResponseStatusEnum.USER_ACCOUNT_NOT_EXIST);
        } else {
            // 查找角色，实际应该查询权限，但数据库没有设计所以就查角色
            List<SysRole> roles = sysRoleService.getRolesByUsername(username);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (SysRole role: roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleKey()));
            }
            logger.debug("AuthUserDetailsServiceImpl-loadUserByUsername......user ===> " + user);
//            System.out.println(new LoginUser(user, authorities));
            return LoginUser.builder()
                    .user(user)
                    .authorities(authorities)
                    .build();
        }

    }
}
