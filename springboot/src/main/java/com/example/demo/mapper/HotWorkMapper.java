package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.HotWork;

public interface HotWorkMapper extends BaseMapper<HotWork> {
    // 删除热点文化作品，将isHotWork字段置为0
    boolean deleteByIds(String ids);
}
