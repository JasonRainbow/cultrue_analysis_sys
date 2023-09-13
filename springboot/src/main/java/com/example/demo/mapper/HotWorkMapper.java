package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.HotWork;
import org.apache.ibatis.annotations.Param;

public interface HotWorkMapper extends BaseMapper<HotWork> {
    // 删除热点文化作品，将isHotWork字段置为0
    boolean deleteByIds(String ids);

    // 删除所有的热点文化作品
    boolean deleteAllHotWorks();

    // 将作品设置为热点文化作品
    boolean updateIsHotWork(Integer workId);

    // 设置作品的传播效果得分
    boolean updateEffectScore(@Param("workId") Integer workId,
                              @Param("score") Double score);
}
