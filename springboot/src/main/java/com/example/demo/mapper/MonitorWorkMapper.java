package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Admin;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.vo.RecommendWorkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 更新MySql数据库中监测作品状态的接口
 */
public interface MonitorWorkMapper extends BaseMapper<MonitorWork> {
    // 根据用户id查询出他所申请的监测作品，包括默认用户root所申请的监测作品
    List<MonitorWork> selectByUserId(Integer userId);
    //根据某个用户的id分页查询其所有的监测作品申请
    Page<MonitorWork> selectByUserIdPaging(Page<MonitorWork> page, @Param("userId") Integer userId);
    //根据作品id查询作品名称
    String findWorkName(Integer workId);
    //数据爬取是否完成
    boolean updateCrawlState(@Param("workId") Integer workId,
                             @Param("state") Integer state);
    //词频分析是否完成
    boolean updateWordFreqState(@Param("workId") Integer workId,
                                @Param("state") Integer state);
    //极性情感分析是否完成
    boolean updatePolarityState(@Param("workId") Integer workId,
                                @Param("state") Integer state);
    //情感分析是否完成
    boolean updateSentimentState(@Param("workId") Integer workId,
                                @Param("state") Integer state);

    List<RecommendWorkVO> selectRecommendWorksByUserId(@Param("userId") Integer userId);
}
