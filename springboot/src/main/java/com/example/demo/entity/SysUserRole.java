package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和角色关联表
 */
@TableName("sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户角色关联实体类", description = "用户角色关联实体类")
public class SysUserRole {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 用户和角色关联的ID

    @ApiModelProperty(value = "用户ID")
    private Integer userId; // 用户ID

    @ApiModelProperty(value = "角色ID")
    private Integer roleId; // 角色ID

    @TableField(exist = false)
    private String username; // 用户名

    @TableField(exist = false)
    private String nickName; // 用户昵称

    @TableField(exist = false)
    private String roleKey; // 角色标识

    @TableField(exist = false)
    private String roleName; // 角色名称
}
