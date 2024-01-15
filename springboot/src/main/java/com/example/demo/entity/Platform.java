package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author hzx
 * 平台类
 */
@TableName("platform")
@ApiModel(value = "平台实体", description = "平台实体")
@Data
@Builder
public class Platform {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 平台名称
     */
    @ApiModelProperty(value = "平台名称")
    private String platformName;

    /**
     * 平台网址
     */
    @ApiModelProperty(value = "平台网址")
    private String platformUrl;

    /**
     * 平台类别
     */
    @ApiModelProperty(value = "平台类别")
    private String platformCategory;

    /**
     * 平台所属国家
     */
    @ApiModelProperty(value = "平台所属国家")
    private String country;
}
