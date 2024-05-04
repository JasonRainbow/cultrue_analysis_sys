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

@TableName("effect_prediction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "传播效果预测实体类", description = "传播效果预测实体类")
public class EffectPrediction {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 作品ID
     */
    @ApiModelProperty(value = "作品ID")
    private Integer workId;

    /**
     * 预测值对应的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "预测值对应的时间")
    private Date date;

    /**
     * 真实的积极情感占比
     */
    @ApiModelProperty(value = "真实的积极情感占比")
    private Double truePosProportion;

    /**
     * 真实的消极情感占比
     */
    @ApiModelProperty(value = "真实的消极情感占比")
    private Double trueNegProportion;

    /**
     * 真实的中立情感占比
     */
    @ApiModelProperty(value = "真实的中立情感占比")
    private Double trueNeuProportion;

    /**
     * 真实的传播效果得分
     */
    @ApiModelProperty(value = "真实的传播效果得分")
    private Double trueScore;


    /**
     * 预测的积极情感占比
     */
    @ApiModelProperty(value = "预测的积极情感占比")
    private Double predPosProportion;

    /**
     * 预测的消极情感占比
     */
    @ApiModelProperty(value = "预测的消极情感占比")
    private Double predNegProportion;

    /**
     * 预测的中立情感占比
     */
    @ApiModelProperty(value = "预测的中立情感占比")
    private Double predNeuProportion;

    /**
     * 预测的传播效果得分
     */
    @ApiModelProperty(value = "预测的传播效果得分")
    private Double predScore;
}
