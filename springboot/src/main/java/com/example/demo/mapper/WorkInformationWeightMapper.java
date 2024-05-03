package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WorkInformationWeight;
import com.example.demo.entity.vo.IMDbData;
import org.apache.ibatis.annotations.Param;

public interface WorkInformationWeightMapper extends BaseMapper<WorkInformationWeight> {

    public IMDbData getIMDbData(@Param(value="platform") String platform);
}
