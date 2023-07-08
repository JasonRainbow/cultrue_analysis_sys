package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.dto.CommentPlatformDto;
import com.example.demo.entity.RawComment;

import java.util.List;

public interface RawCommentMapper extends BaseMapper<RawComment> {
    List<CommentPlatformDto> countPlatformByWorkId(Integer workId);

    // 查询所有的国家
    List<String> selectAllCountry();

    // 查询所有的平台
    List<String> selectAllPlatform();
}
