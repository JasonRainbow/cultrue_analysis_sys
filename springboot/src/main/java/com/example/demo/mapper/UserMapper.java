package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.UserWorkUnitDto;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    // 一对多查询
    Page<User> findPage(Page<User> page,
                        @Param("username") String username,
                        @Param("name") String name);

    // 统计各个单位的人数
    List<UserWorkUnitDto> countWorkUnit();

    // 查询用户名
    User selectByUsername(String username);

    int updatePass(Map<String, Object> map);

    // 查询申请监测某个作品的用户邮箱列表
    List<User> selectEmailsByWorkId(Integer workId);
}
