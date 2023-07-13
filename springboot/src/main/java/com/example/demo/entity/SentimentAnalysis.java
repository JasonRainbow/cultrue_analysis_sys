package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("sentiment_analy")
@Data
public class SentimentAnalysis {
    @TableId(type = IdType.AUTO)
    private Integer id; // 自增主键

    private Integer workId; // 作品id

    @TableField(exist = false)
    private MonitorWork monitorWork; // 监测作品

    private String country; // 所属国家

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time; // 情感极性分析的评论发布时间

    private String platform; // 平台

    private Integer happy; // 开心的评论数

    private Integer amazed; // 惊讶的评论数

    private Integer neutrality; // 中立的评论数

    private Integer hate; // 讨厌的评论数

    private Integer angry; // 愤怒的评论数

    private Integer fear; // 恐惧的评论数
}
