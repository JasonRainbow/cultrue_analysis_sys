package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.CommentPlatformDto;
import com.example.demo.entity.RawComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RawCommentMapper extends BaseMapper<RawComment> {
    List<CommentPlatformDto> countPlatformByWorkId(Integer workId);

    // 查询所有的国家
    List<String> selectAllCountry();

    // 查询所有的平台
    List<String> selectAllPlatform();

    // 分页模糊查询
    Page<RawComment> selectPage2(Page<RawComment> page,
                                 @Param("workName") String workName,
                                 @Param("content") String content,
                                 @Param("country") String country,
                                 @Param("platform") String platform,
                                 @Param("postTime") String postTime);
}
