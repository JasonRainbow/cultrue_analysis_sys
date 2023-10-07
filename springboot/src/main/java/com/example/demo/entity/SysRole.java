package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色实体类
 */
@TableName("sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户角色实体类", description = "用户角色实体类")
public class SysRole {
    @TableId
    private Integer id; // 角色ID

    private String roleKey; // 角色标识

    private String roleName; // 角色名称

}
