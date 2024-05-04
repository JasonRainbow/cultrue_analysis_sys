package com.example.demo.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "", description = "Youtube上辅助指标信息")
public class YoutubeData {

    private Double maxZanNum;

    private Double minZanNum;

    private Integer maxCommentNum;

    private Integer minCommentNum;

    private Integer maxViewNum;

    private Integer minViewNum;

    private Double normalizeZanNum;

    private Double normalizeCommentNum;

    private Double normalizeViewNum;
}
