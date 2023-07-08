package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.PolarityAnalysis;
import com.example.demo.entity.dto.PolarityDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PolarityAnalysisMapper extends BaseMapper<PolarityAnalysis> {
    Page<PolarityAnalysis> findPage(Page<PolarityAnalysis> page,
                                    @Param("workName") String workName,
                                    @Param("country") String country,
                                    @Param("platform") String platform,
                                    @Param("time") String time
                                    );

    List<PolarityAnalysis> findAll();

    // 查询一个日期区间的情感记性分析统计信息
    List<PolarityDto> selectDayInterval(@Param("workId") Integer workId,
                                        @Param("country") String country,
                                        @Param("startTime") String startTime,
                                        @Param("interval") Integer interval);
}
