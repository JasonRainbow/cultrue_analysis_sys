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


@TableName("monitor_work")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitorWork {
    @TableId(type = IdType.AUTO)
    private Integer id; // 自增主键

    private String name; // 文化作品名称

    private String category; // 类别

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; // 监测作品的创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime; // 完成作品监测的时间

    private Short crawlOk = 0; // 是否完成数据爬取

    private Short sentimentOk = 0; // 是否完成情感分析

    private Short polarityOk = 0; // 是否完成情感极性分析

    private Short wordCloudOk = 0; // 是否完成词云图分析

    private Short gramNetOk = 0; // 是否完成语义网络分析

    private Short allDone = 0; // 是否完成所有监测工作
}
