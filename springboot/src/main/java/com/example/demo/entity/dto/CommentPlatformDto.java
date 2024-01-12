package com.example.demo.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "评论平台统计实体", description = "评论平台统计实体")
public class CommentPlatformDto {
    @ApiModelProperty(value = "平台")
    private String platform;

    @ApiModelProperty(value = "评论数")
    private Integer count;

    @ApiModelProperty(value = "占比")
    private Double proportion;
}
