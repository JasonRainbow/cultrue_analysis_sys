package com.example.demo.service;

import com.example.demo.entity.EffectPrediction;

import java.util.List;

public interface EffectPredictionService {
    /**
     * 根据作品ID和年份查询传播效果预测结果
     * @param workId 作品ID
     * @param year 年份
     * @return EffectPrediction列表
     */
    List<EffectPrediction> findByWorkIdAndYear(Integer workId, Integer year);
}
