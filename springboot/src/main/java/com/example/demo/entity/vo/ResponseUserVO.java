package com.example.demo.entity.vo;

import com.example.demo.entity.model.LoginUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * 用户登录后返回的用户信息类
 */
@Data
@NoArgsConstructor
public class ResponseUserVO {
    private Integer id; // 用户ID

    private Boolean isAdmin = false; // 是否为超级管理员

    private String username; // 用户名

    private String nickName; // 用户昵称

    private String avatar; // 用户头像

    private String email; // 用户邮箱

    private String phone; // 用户手机号

    public ResponseUserVO(LoginUser loginUser) {
        this.id = loginUser.getUser().getId();
        this.username = loginUser.getUsername();
        this.nickName = loginUser.getUser().getName();
        this.avatar = loginUser.getUser().getAvatar();
        this.email = loginUser.getUser().getEmail();
        this.phone = loginUser.getUser().getPhone();
        for (GrantedAuthority authority: loginUser.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_admin")) {
                this.isAdmin = true;
                break;
            }
        }
    }
}
