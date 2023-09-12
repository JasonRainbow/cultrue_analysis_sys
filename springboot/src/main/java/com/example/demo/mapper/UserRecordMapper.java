package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserRecordMapper extends BaseMapper<User> {
    //添加用户查看记录
    int addUserRecord(UserRecord userRecord);
    //分页查询用户的查看记录
    Page<workRecord> selectRecordByUserIdPaging(Page<workRecord> page, @Param("userId") Integer userId);

    int selectUserRecentRecord(@Param("userId") Integer userId, @Param("workId") Integer workId);

    int addUserRecentRecord(UserRecentRecord userRecentRecord);

    int updateUserRecentRecord(@Param("userId") Integer userId, @Param("workId") Integer workId, @Param("date") Date date);

    Page<workRecentRecord> selectRecentRecordByUserIdPaging(Page<workRecentRecord> page, @Param("userId") Integer userId);
}
