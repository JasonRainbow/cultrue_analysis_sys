package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@TableName("sentiment_analy")
@Data
@ApiModel(value = "情感分析实体", description = "情感分析实体")
public class SentimentAnalysis {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "监测作品ID")
    private Integer workId; // 监测作品id

    @TableField(exist = false)
    @ApiModelProperty(value = "监测作品")
    private MonitorWork monitorWork; // 监测作品

    @ApiModelProperty(value = "所属国家")
    private String country; // 所属国家

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "评论发布时间")
    private Date time; // 情感极性分析的评论发布时间

    @ApiModelProperty(value = "所属平台")
    private String platform; // 平台

    @ApiModelProperty(value = "开心评论数")
    private Integer happy; // 开心的评论数

    @ApiModelProperty(value = "惊讶评论数")
    private Integer amazed; // 惊讶的评论数

    @ApiModelProperty(value = "中立评论数")
    private Integer neutrality; // 中立的评论数

    @ApiModelProperty(value = "厌恶评论数")
    private Integer hate; // 讨厌的评论数

    @ApiModelProperty(value = "愤怒评论数")
    private Integer angry; // 愤怒的评论数

    @ApiModelProperty(value = "恐惧评论数")
    private Integer fear; // 恐惧的评论数
}
