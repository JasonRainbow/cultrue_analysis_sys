package com.example.demo.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 用户的推荐作品类
 */
@Data
@Slf4j
public class RecommendWorkVO {
    @TableId
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "用户ID")
    private Integer userId; // 用户ID

    @ApiModelProperty(value = "用户名")
    private String username; // 用户名

    @ApiModelProperty(value = "作品ID")
    private Integer workId; // 作品ID

    @ApiModelProperty(value = "作品名")
    private String workName; // 作品名

    @ApiModelProperty(value = "作品类别")
    private String category; // 作品类别

    @ApiModelProperty(value = "作品介绍内容")
    private String content; // 作品介绍内容

    @ApiModelProperty(value = "作品标签")
    private String labels; // 作品标签

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "作品发行时间")
    private Date postTime; // 作品发行时间

    @ApiModelProperty(value = "作品介绍网址")
    private String citeUrl; // 作品介绍网址

    @ApiModelProperty(value = "作品缩略图网址")
    private String imgUrl; // 作品缩略图网址

    @ApiModelProperty(value = "作品推荐指数")
    private Float score; // 作品推荐指数
}
