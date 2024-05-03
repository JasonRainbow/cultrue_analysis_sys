package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WorkInformation;
import com.example.demo.entity.vo.DoubanData;
import org.apache.ibatis.annotations.Param;

public interface WorkInformationMapper extends BaseMapper<WorkInformation> {

    public DoubanData getDoubanData(@Param(value = "platform") String platform);
}
