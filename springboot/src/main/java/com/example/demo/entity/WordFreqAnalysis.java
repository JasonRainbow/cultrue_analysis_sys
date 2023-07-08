package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("word_freq_analy")
@Data
public class WordFreqAnalysis {
    @TableId(type = IdType.AUTO)
    private Integer id; // 自增主键

    private Integer workId; // 作品id

    @TableField(exist = false)
    private MonitorWork monitorWork; // 监测作品

    private String country; // 所属国家

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time; // 情感极性分析的评论发布时间

    private String platform; // 平台

    private String polarity; // 评论中的高频词的情感极性数组

    private String keywords; // 评论中出现的高频词数组

    private String frequency; // 评论中出现的高频词频数数组
}
