package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

@TableName("raw_comment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "作品评论实体", description = "作品评论实体")
public class RawComment {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "评论内容")
    private String content; // 评论的具体内容

    @ApiModelProperty(value = "翻译后的评论")
    private String translated; // 翻译后的评论

    @ApiModelProperty(value = "点赞数")
    private Integer likes; // 评论的点赞数

    @ApiModelProperty(value = "监测作品ID")
    private Integer workId; // 监测作品id

    @TableField(exist = false)
    @ApiModelProperty(value = "监测作品")
    private MonitorWork monitorWork; // 评论所属的文化作品

    @ApiModelProperty(value = "评论的情感倾向")
    private String sentiment; // 评论的情感倾向

    @ApiModelProperty(value = "所属国家")
    private String country; // 评论所属的国家

    @ApiModelProperty(value = "所属平台")
    private String platform; // 评论所属的平台

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "评论发布时间")
    private Date postTime; // 评论发布的时间  精确到天
}
