package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "监测作品实体", description = "监测作品实体")
public class MonitorWork {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "文化作品名称")
    private String name; // 文化作品名称

    @ApiModelProperty(value = "作品类型")
    private String category; // 类别

    @ApiModelProperty(value = "作品标签")
    private String labels; // 作品标签

    @ApiModelProperty(value = "传播效果得分")
    private Float effectScore; // 传播效果得分

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "监测作品的创建时间")
    private Date createTime; // 监测作品的创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "完成作品监测的时间")
    private Date endTime; // 完成作品监测的时间

    @ApiModelProperty(value = "是否完成数据爬取")
    private Short crawlOk = 0; // 是否完成数据爬取

    @ApiModelProperty(value = "是否完成情感分析")
    private Short sentimentOk = 0; // 是否完成情感分析

    @ApiModelProperty(value = "是否完成情感极性分析")
    private Short polarityOk = 0; // 是否完成情感极性分析

    @ApiModelProperty(value = "是否完成词频统计")
    private Short wordFreqOk = 0; // 是否完成词频统计

    @ApiModelProperty(value = "是否完成所有监测工作")
    private Short allDone = 0; // 是否完成所有监测工作
}
