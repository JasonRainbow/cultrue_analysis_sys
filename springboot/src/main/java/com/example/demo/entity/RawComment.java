package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("raw_comment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawComment {
    @TableId(type = IdType.AUTO)
    private Integer id; // 自增主键

    private String content; // 评论的具体内容

    private Integer likes; // 评论的点赞数

    private Integer workId; // 监测作品id

    @TableField(exist = false)
    private MonitorWork monitorWork; // 评论所属的文化作品

    private String sentiment; // 评论的情感倾向

    private String country; // 评论所属的国家

    private String platform; // 评论所属的平台

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date postTime; // 评论发布的时间  精确到天
}
