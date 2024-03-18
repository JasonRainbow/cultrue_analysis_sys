package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("comment_subject")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "评论属性观点主题", description = "评论属性观点主题")
public class CommentSubject {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "所属评论ID")
    private Integer commentId; // 所属评论ID

    @ApiModelProperty(value = "所属作品ID")
    private Integer workId; // 所属作品ID

    @ApiModelProperty(value = "属性词")
    private String propertyWord; // 属性词

    @ApiModelProperty(value = "观点词")
    private String opinionWord; // 观点词

    @ApiModelProperty(value = "情感倾向")
    private String sentiment; // 情感倾向

    @ApiModelProperty(value = "所属主题")
    private String subjects; // 情感倾向
}
