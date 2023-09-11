package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRecord;
import com.example.demo.entity.workRecord;
import org.apache.ibatis.annotations.Param;

public interface UserRecordMapper extends BaseMapper<User> {
    int addUserRecord(UserRecord userRecord);

    Page<workRecord> selectRecordByUserIdPaging(Page<workRecord> page, @Param("userId") Integer userId);
}
