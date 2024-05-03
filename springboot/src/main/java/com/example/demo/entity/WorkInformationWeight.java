package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "作品信息权重实体类", description = "存储作品在平台上的相关指标的权重")
public class WorkInformationWeight {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "平台")
    private String platform;

    @ApiModelProperty(value = "作品得分权重")
    private Double scoreWeight;

    @ApiModelProperty(value = "作品点赞数量权重")
    private Double zanNumWeight;

    @ApiModelProperty(value = "作品评论数量权重")
    private Double commentNumWeight;

    @ApiModelProperty(value = "作品收藏量权重")
    private Double collectionWeight;

    @ApiModelProperty(value = "作品流行度权重")
    private Double popularityWeight;

    @ApiModelProperty(value = "作品指标权重更新时间")
    private Date updateTime;
}
