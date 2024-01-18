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

@Data
@TableName("monitor_work")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "热点文化作品实体", description = "热点文化作品实体")
public class HotWork {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "热点作品名称")
    private String name; // 热点作品名称

    @ApiModelProperty(value = "热点作品的英文名称")
    private String englishName; // 热点作品的英文名称

    @ApiModelProperty(value = "作品类型")
    private String category; // 作品类型

    @ApiModelProperty(value = "作品子类别")
    private String subCategory; // 作品子类别

    @ApiModelProperty(value = "作品标签")
    private String labels; // 作品标签

    @ApiModelProperty(value = "作品介绍内容")
    private String content; // 作品介绍内容

    @ApiModelProperty(value = "翻译后的作品介绍内容")
    private String translatedContent; // 翻译后的作品介绍内容

    @ApiModelProperty(value = "作品介绍url")
    private String citeUrl; // 网址

    @ApiModelProperty(value = "缩略图url")
    private String imgUrl; // 图片url

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期")
    private Date postTime; // 发布日期

    private Integer isHotWork;

    private Integer sourcePlatform;

    @TableField(exist = false)
    private Platform platform;
}
