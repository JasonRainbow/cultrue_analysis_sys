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

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 文化作品名称
     */
    @ApiModelProperty(value = "文化作品名称")
    private String name;

    /**
     * 文化作品的英文名称
     */
    @ApiModelProperty(value = "文化作品的英文名称")
    private String englishName;

    /**
     * 类别
     */
    @ApiModelProperty(value = "作品类型")
    private String category;

    /**
     * 子类别
     */
    @ApiModelProperty(value = "作品的子类型")
    private String subCategory;

    @ApiModelProperty(value = "作品标签")
    private String labels;

    /**
     * 作品的作者
     */
    @ApiModelProperty(value = "作品的作者")
    private String author;

    /**
     * 作品所属的地域
     */
    @ApiModelProperty(value = "作品所属的地域")
    private String origin;

    /**
     * 是否为热点文化作品
     */
    @ApiModelProperty(value = "是否为热点文化作品")
    private String isHotWork;

    /**
     * 是否为湖湘文化作品
     */
    @ApiModelProperty(value = "是否为湖湘文化作品")
    private String isHunanWork;

    /**
     * 传播效果得分
     */
    @ApiModelProperty(value = "传播效果得分")
    private Float effectScore;

    /**
     * 监测作品的创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "监测作品的创建时间")
    private Date createTime;

    /**
     * 完成作品监测的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "完成作品监测的时间")
    private Date endTime;

    /**
     * 作品发布的日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "作品发布的日期")
    private Date postTime;

    /**
     * 是否完成数据爬取
     */
    @ApiModelProperty(value = "是否完成数据爬取")
    private Short crawlOk = 0;

    /**
     * 是否完成情感分析
     */
    @ApiModelProperty(value = "是否完成情感分析")
    private Short sentimentOk = 0;

    /**
     * 是否完成情感极性分析
     */
    @ApiModelProperty(value = "是否完成情感极性分析")
    private Short polarityOk = 0;

    /**
     * 是否完成词频统计
     */
    @ApiModelProperty(value = "是否完成词频统计")
    private Short wordFreqOk = 0;

    /**
     * 是否完成所有监测工作
     */
    @ApiModelProperty(value = "是否完成所有监测工作")
    private Short allDone = 0;

    /**
     * 作品简介
     */
    @ApiModelProperty(value = "作品简介")
    private String content;

    /**
     * 作品引用网址
     */
    @ApiModelProperty(value = "作品引用网址")
    private String citeUrl;

    /**
     * 作品图片网址
     */
    @ApiModelProperty(value = "作品图片网址")
    private String imgUrl;
}
