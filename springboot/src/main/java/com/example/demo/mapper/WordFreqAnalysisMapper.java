package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.WordFreqAnalysis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WordFreqAnalysisMapper extends BaseMapper<WordFreqAnalysis> {
    List<WordFreqAnalysis> findAll();

    Page<WordFreqAnalysis> findPage(Page<WordFreqAnalysis> page,
                                     @Param("workName") String workName,
                                     @Param("country") String country,
                                     @Param("platform") String platform,
                                     @Param("time") String time
    );
}
