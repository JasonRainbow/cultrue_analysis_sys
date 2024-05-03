package com.example.demo.entity.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "", description = "豆瓣上部分指标信息")
public class DoubanData {
    private Double maxScore;

    private Double minScore;

    private Integer maxCommentNum;

    private Integer minCommentNum;

    private Double normalizeScore;

    private Double normalizeCommentNum;
}
