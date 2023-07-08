package com.example.demo.entity.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
public class PolarityStatisticsDto {
    private Integer workId; // 作品id

    private String workName; // 作品名

    private String country; // 统计的评论所属国家

    private List<PolarityDto> statisticsInfo;
}
