package com.example.demo.entity.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "", description = "IMDb上辅助指标信息")
public class IMDbData {
    private Double maxScore;

    private Double minScore;

    private Integer maxCommentNum;

    private Integer minCommentNum;

    private Integer maxCollection;

    private Integer minCollection;

    private Double normalizeScore;

    private Double normalizeCommentNum;

    private Double normalizeCollection;
}
