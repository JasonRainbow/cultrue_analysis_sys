package com.example.demo.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@ApiModel(value = "文化作品某个月的评论数量", description = "文化作品某个月的评论数量")
public class MonthCommentNum {

    @ApiModelProperty(value = "评论数量")
    Integer count; // 评论数量

    @ApiModelProperty(value = "评论发布月份")
    String time; // 月份
}
