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

@TableName("word_freq_analy")
@Data
@ApiModel(value = "词频统计实体", description = "词频统计实体")
public class WordFreqAnalysis {
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

    @ApiModelProperty(value = "情感极性")
    private String polarity; // 评论中的高频词的情感极性数组

    @ApiModelProperty(value = "高频词")
    private String keywords; // 评论中出现的高频词数组

    @ApiModelProperty(value = "高频词频数")
    private String frequency; // 评论中出现的高频词频数数组
}
