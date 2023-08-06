package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WorkScore;

import java.util.List;

public interface WorkScoreMapper extends BaseMapper<WorkScore> {
    List<WorkScore> countScoreByWorkId(Integer workId);
}
