package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.CoreUser;
import org.apache.ibatis.annotations.Param;

public interface CoreUserMapper extends BaseMapper<CoreUser> {
    IPage<CoreUser> selectCoreUserByPage(Page<CoreUser> page,@Param("workId") Integer workId);
}
