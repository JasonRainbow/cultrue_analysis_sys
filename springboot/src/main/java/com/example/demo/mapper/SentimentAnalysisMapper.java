package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.SentimentAnalysis;
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
}
