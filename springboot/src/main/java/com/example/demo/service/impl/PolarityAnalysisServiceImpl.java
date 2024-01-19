package com.example.demo.service.impl;

import com.example.demo.entity.PolarityAnalysis;
import com.example.demo.entity.dto.PolarityDto;
import com.example.demo.entity.dto.PolarityStatisticsDto;
import com.example.demo.mapper.PolarityAnalysisMapper;
import com.example.demo.service.PolarityAnalysisService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PolarityAnalysisServiceImpl implements PolarityAnalysisService {

    @Autowired
    private PolarityAnalysisMapper polarityAnalysisMapper;

    @Override
    public PolarityStatisticsDto getPolarityByYear(Integer workId, String country, String year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        PolarityStatisticsDto polarityStatisticsDto =
                PolarityStatisticsDto.builder()
                        .workId(workId)
                        .country(country)
                        .statisticsInfo(new ArrayList<>())
                        .build();
        for (int i = 1; i <= 12; i++) {
            String month = String.format("%s-%02d", year, i);
            PolarityDto polarityDto = polarityAnalysisMapper.selectPolarityByMonth(workId, country, month);
            if (polarityDto == null) {
                polarityDto = PolarityDto.builder().positive(0).negative(0).neutrality(0).build();
            }
            try {
                polarityDto.setPostTime(sdf.parse(month));
            } catch (Exception ignored) {

            }

            polarityStatisticsDto.getStatisticsInfo().add(polarityDto);
        }

        return polarityStatisticsDto;
    }

    @Override
    public List<PolarityAnalysis> getWorldPolarityMonthly(Integer workId, String month) throws ParseException {

        List<PolarityAnalysis> polarityAnalyses
                = polarityAnalysisMapper.selectCountriesPolarity(workId, month);

        if (!Strings.isNullOrEmpty(month)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date monthDate = sdf.parse(month);
            for (PolarityAnalysis item: polarityAnalyses) {
                item.setTime(monthDate);
            }
        }

        return polarityAnalyses;
    }

    @Override
    public List<PolarityAnalysis> getWorldPolarityYearly(Integer workId, String year) throws ParseException {

        List<PolarityAnalysis> polarityAnalyses
                = polarityAnalysisMapper.selectCountriesPolarityByYear(workId, year);

        if (!Strings.isNullOrEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date monthDate = sdf.parse(year);
            for (PolarityAnalysis item: polarityAnalyses) {
                item.setTime(monthDate);
            }
        }

        return polarityAnalyses;
    }
}
