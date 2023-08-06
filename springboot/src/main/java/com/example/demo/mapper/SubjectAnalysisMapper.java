package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.SubjectAnalysis;
import org.apache.ibatis.annotations.Param;

public interface SubjectAnalysisMapper extends BaseMapper<SubjectAnalysis> {
    SubjectAnalysis selectByWorkIdAndSubject(@Param("workId") Integer workId,
                                             @Param("subject") String subject);
}
