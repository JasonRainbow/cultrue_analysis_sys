package com.example.demo.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "", description = "爬取的国家评论数量")
public class CountryCommentNum {
    @ApiModelProperty(value = "国家")
    String country; // 国家

    @ApiModelProperty(value = "某个国家的评论数量")
    Integer cnt; // 某种极性情感的评论数
}
