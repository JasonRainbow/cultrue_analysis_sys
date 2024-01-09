package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "作品评论数量实体类", description = "作品评论数量实体类")
public class WorkCommentNum {
    @ApiModelProperty(value = "作品id")
    private Integer id;
    @ApiModelProperty(value = "作品名称")
    private String name;
    @ApiModelProperty(value = "作品评论数量")
    private Integer count;
}
