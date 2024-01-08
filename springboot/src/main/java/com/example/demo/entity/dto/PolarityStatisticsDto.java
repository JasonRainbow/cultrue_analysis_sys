package com.example.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolarityStatisticsDto {
    private Integer workId; // 作品id

    private String workName; // 作品名

    private String country; // 统计的评论所属国家

    private List<PolarityDto> statisticsInfo;
}
