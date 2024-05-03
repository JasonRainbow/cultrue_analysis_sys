package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "作品信息实体类", description = "存储作品在平台上的相关信息")
public class WorkInformation {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "作品id")
    private Integer workId;

    @ApiModelProperty(value = "作品名称")
    private String workName;

    @ApiModelProperty(value = "所属平台")
    private String platform;

    @ApiModelProperty(value = "作品评分")
    private Double score;

    @ApiModelProperty(value = "作品点赞数量")
    private Integer zanNum;

    @ApiModelProperty(value = "作品评论数量")
    private Integer commentNum;

    @ApiModelProperty(value = "作品收藏量")
    private Integer collection;

    @ApiModelProperty(value = "作品流行度")
    private Double popularity;

    @ApiModelProperty(value = "作品信息更新时间")
    private Date updateTime;


}
