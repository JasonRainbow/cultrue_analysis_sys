package com.example.demo.service;

import com.example.demo.entity.dto.PolarityStatisticsDto;
import org.springframework.stereotype.Service;

public interface PolarityAnalysisService {
    /**
     * 根据作品ID、国家和年份查询这一年内每个月的极性情感
     * @return PolarityStatisticsDto
     */
    PolarityStatisticsDto getPolarityByYear(Integer workId, String country, String year);
}
