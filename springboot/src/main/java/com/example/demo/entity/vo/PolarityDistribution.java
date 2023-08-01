package com.example.demo.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "极性情感分布", description = "极性情感分布")
public class PolarityDistribution {
    @ApiModelProperty(value = "国家")
    String country; // 国家

    @ApiModelProperty(value = "某种极性情感的评论数")
    Integer cnt; // 某种极性情感的评论数
}
