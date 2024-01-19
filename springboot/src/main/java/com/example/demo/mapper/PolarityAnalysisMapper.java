package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.PolarityAnalysis;
import com.example.demo.entity.dto.PolarityDto;
import com.example.demo.entity.vo.PolarityDistribution;
import com.example.demo.entity.vo.PolarityMonthlyVO;
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

    // 查询一个日期区间的情感极性分析统计信息
    List<PolarityDto> selectDayInterval(@Param("workId") Integer workId,
                                        @Param("country") String country,
                                        @Param("startTime") String startTime,
                                        @Param("offsets") List<Integer> offsets);

    List<PolarityDistribution> findPolarityDis(@Param("workId") Integer workId,
                                               @Param("type") String type,
                                               @Param("offset") Integer offset);

    // 查询一个月份区间的情感极性分析统计信息
    List<PolarityMonthlyVO> selectMonthInterval(@Param("country") String country,
                                                @Param("workId") Integer workId,
                                                @Param("offsets") List<Integer> offsets);

    // 查询国家列表
    List<String> selectCountries(Integer workId);

    // 统计每个作品的积极、消极、中立情感总数
    List<PolarityAnalysis> selectWorksPolarity();

    //查询某个月的极性情感分析结果
    PolarityDto selectPolarityByMonth(@Param("workId") Integer workId,
                                      @Param("country") String country,
                                      @Param("selectMonth") String selectMonth);

    List<PolarityAnalysis> selectCountriesPolarity(@Param("workId") Integer workId,
                                                   @Param("selectMonth") String selectMonth);

    List<PolarityAnalysis> selectCountriesPolarityByYear(@Param("workId") Integer workId,
                                                   @Param("selectYear") String selectYear);
}
