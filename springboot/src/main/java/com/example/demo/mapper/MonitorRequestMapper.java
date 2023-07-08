package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.MonitorRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MonitorRequestMapper extends BaseMapper<MonitorRequest> {
    Page<MonitorRequest> findPage(Page<MonitorRequest> page,
                                  @Param("username") String username,
                                  @Param("workName") String workName);

    List<MonitorRequest> selectAll(@Param("username") String username,
                                   @Param("workName") String workName);

    Page<MonitorRequest> selectByUserId(Page<MonitorRequest> page,
                                        Integer userId);
}
