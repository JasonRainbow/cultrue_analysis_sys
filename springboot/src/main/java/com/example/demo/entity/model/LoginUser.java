package com.example.demo.entity.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 实现UserDetails，仿写User
 */
@Data
@Builder
@AllArgsConstructor // 全参构造
@NoArgsConstructor  // 无参构造
public class LoginUser implements UserDetails {

    private String UUID;

    private User user;

//    @JSONField(serialize = false) // 该字段不序列化
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * 账号是否未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未被锁
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭据是否未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 获取密码
     * @return 密码
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

}
