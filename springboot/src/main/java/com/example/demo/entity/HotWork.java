package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("hot_work")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotWork {
    @TableId(type = IdType.AUTO)
    private Integer id; // 自增主键

    private String name; // 热点作品名称

    private String category; // 作品类型

    private String title; // 作品介绍标题

    private String content; // 作品介绍内容

    private String citeUrl; // 网址

    private String imgUrl; // 图片url

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date postTime; // 发布日期
}
