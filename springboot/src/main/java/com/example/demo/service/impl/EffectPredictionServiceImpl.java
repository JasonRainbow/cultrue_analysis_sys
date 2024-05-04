package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.EffectPrediction;
import com.example.demo.mapper.EffectPredictionMapper;
import com.example.demo.service.EffectPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class EffectPredictionServiceImpl implements EffectPredictionService {

    @Autowired
    private EffectPredictionMapper effectPredictionMapper;

    @Override
    public List<EffectPrediction> findByWorkIdAndYear(Integer workId, Integer year) {
        Calendar calendar = Calendar.getInstance();
        LambdaQueryWrapper<EffectPrediction> query = new LambdaQueryWrapper<>();
        query.eq(EffectPrediction::getWorkId, workId);
        List<EffectPrediction> effectPredictions = effectPredictionMapper.selectList(query);
        if (year == -1) {
            return effectPredictions;
        }
        List<EffectPrediction> res = new ArrayList<>();
        for (EffectPrediction e: effectPredictions) {
            calendar.setTime(e.getDate());
            if (calendar.get(Calendar.YEAR) == year) {
                res.add(e);
            }
        }
        return res;
    }
}
