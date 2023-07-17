package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@TableName("admin")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "管理员实体", description = "管理员实体")
public class Admin {
    @TableId(type = IdType.AUTO) // 自动增长
    @ApiModelProperty(value = "主键")
    private Integer id; // 编号id

    @ApiModelProperty(value = "管理员账号/用户名")
    private String username; // 管理员账号

    @ApiModelProperty(value = "密码")
    private String password; // 密码

    @ApiModelProperty(value = "名称/昵称")
    private String name; // 名字

    @ApiModelProperty(value = "管理员级别")
    private int level;

    @ApiModelProperty(value = "头像url")
    private String avatar; // 头像的url

    @ApiModelProperty(value = "手机号码")
    private String phone; // 手机号码

    @ApiModelProperty(value = "邮箱")
    private String email; // 邮箱

    @TableField(exist = false)
    @ApiModelProperty(value = "管理员令牌")
    private String token; // 用户凭证
}
