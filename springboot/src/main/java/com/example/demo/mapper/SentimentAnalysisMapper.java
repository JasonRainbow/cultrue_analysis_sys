package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.SentimentAnalysis;
import com.example.demo.entity.dto.SentimentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SentimentAnalysisMapper extends BaseMapper<SentimentAnalysis> {
    List<SentimentAnalysis> findAll();

    Page<SentimentAnalysis> findPage(Page<SentimentAnalysis> page,
                                    @Param("workName") String workName,
                                    @Param("country") String country,
                                    @Param("platform") String platform,
                                     @Param("time") String time
    );

    List<SentimentAnalysis> findByWorkIdAndTime(@Param("workId") Integer workId,
                                                @Param("time") String time);

    // 查询某个月的细腻情感分析结果
    SentimentDto selectSentimentByMonth(@Param("workId") Integer workId,
                                        @Param("country") String country,
                                        @Param("selectMonth") String selectMonth);
}
