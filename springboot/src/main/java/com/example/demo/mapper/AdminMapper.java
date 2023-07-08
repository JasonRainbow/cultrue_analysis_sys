package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface AdminMapper extends BaseMapper<Admin> {
    Admin selectByUsername(String username);

    Page<Admin> findByPage(Page<Admin> page, @Param("username") String username,
                           @Param("name") String name);

    int updatePass(Map<String, Object> map);
}
