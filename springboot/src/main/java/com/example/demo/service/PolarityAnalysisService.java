package com.example.demo.service;

import com.example.demo.entity.PolarityAnalysis;
import com.example.demo.entity.dto.PolarityStatisticsDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

public interface PolarityAnalysisService {
    /**
     * 根据作品ID、国家和年份查询这一年内每个月的极性情感
     * @return PolarityStatisticsDto
     */
    PolarityStatisticsDto getPolarityByYear(Integer workId, String country, String year);

    /**
     * 查询指定作品的世界极性情感分布  指定月份
     * @param workId 作品ID
     * @param month 查询月份
     * @return 极性情感分布列表
     */
    List<PolarityAnalysis> getWorldPolarityMonthly(Integer workId, String month) throws ParseException;

    /**
     * 查询指定作品的世界极性情感分布  指定年份
     * @param workId 作品ID
     * @param year 查询年份
     * @return 极性情感分布列表
     */
    List<PolarityAnalysis> getWorldPolarityYearly(Integer workId, String year) throws ParseException;
}
