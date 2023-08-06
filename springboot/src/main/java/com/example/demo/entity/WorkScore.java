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

@TableName("work_score")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "作品得分", description = "作品得分实体类")
public class WorkScore {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "监测作品ID")
    private Integer workId; // 监测作品id

    @ApiModelProperty(value = "作品得分")
    private Float score; // 作品得分

    @ApiModelProperty(value = "平台")
    private String platform; // 平台

    @ApiModelProperty(value = "评分爬取时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime; // 评分爬取时间
}
