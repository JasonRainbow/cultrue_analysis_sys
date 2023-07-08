package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("admin")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
    @TableId(type = IdType.AUTO) // 自动增长
    private Integer id; // 编号id

    private String username; // 管理员账号

    private String password; // 密码

    private String name; // 名字

    private int level;

    private String avatar; // 头像的url

    private String phone; // 手机号码

    private String email; // 邮箱

    @TableField(exist = false)
    private String token; // 用户凭证
}
