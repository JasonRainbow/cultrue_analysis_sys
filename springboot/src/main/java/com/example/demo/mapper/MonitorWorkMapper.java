package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.MonitorWork;

import java.util.List;

public interface MonitorWorkMapper extends BaseMapper<MonitorWork> {
    // 根据用户id查询出他所申请的监测作品，包括默认用户root所申请的监测作品
    List<MonitorWork> selectByUserId(Integer userId);

    String findWorkName(Integer workId);

    boolean updateCrawlState(Integer workId);
}
