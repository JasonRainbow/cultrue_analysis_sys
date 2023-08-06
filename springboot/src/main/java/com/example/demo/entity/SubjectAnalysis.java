package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("subject_analysis")
@Data
@ApiModel(value = "作品评论主题分析实体类", description = "作品评论主题分析实体类")
public class SubjectAnalysis {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "监测作品ID")
    private Integer workId; // 监测作品id

    @ApiModelProperty(value = "评论主题")
    private String subject; // 评论主题

    @ApiModelProperty(value = "积极主题关键词数")
    private Integer positive; // 积极主题关键词数

    @ApiModelProperty(value = "消极主题关键词数")
    private Integer negative; // 消极主题关键词数

    @ApiModelProperty(value = "中立主题关键词数")
    private Integer neutrality; // 中立主题关键词数

    @ApiModelProperty(value = "所有主题关键词")
    private String keywords; // 所有主题关键词

    @ApiModelProperty(value = "所有主题关键词词频")
    private String wordFreq; // 所有主题关键词词频
}
