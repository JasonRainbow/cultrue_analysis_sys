package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("core_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoreUser {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像url")
    private String avatar;
    @ApiModelProperty(value = "文化作品id")
    private Integer workId;
    @ApiModelProperty(value = "所在平台")
    private String platformId;
    @ApiModelProperty(value = "主影响力指数")
    private double effectIndex;
}
