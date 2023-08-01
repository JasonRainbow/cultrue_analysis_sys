package com.example.demo.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "月统计的极性情感分析")
public class PolarityMonthlyVO {
    @ApiModelProperty(value = "日期（月份）")
    private String postTime;

    @ApiModelProperty("积极情感评论数")
    private Integer positive = 0;

    @ApiModelProperty("消极情感评论数")
    private Integer negative = 0;

    @ApiModelProperty("中立情感评论数")
    private Integer neutrality = 0;
}
