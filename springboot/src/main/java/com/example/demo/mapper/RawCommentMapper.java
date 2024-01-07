package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.CommentPlatformDto;
import com.example.demo.entity.RawComment;
import com.example.demo.entity.vo.CountryCommentNum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RawCommentMapper extends BaseMapper<RawComment> {
    List<CommentPlatformDto> countPlatformByWorkId(Integer workId);

    // 查询作品id为workId的作品的所有评论发布国家
    List<String> selectAllCountry(@Param("workId") Integer workId);

    // 查询所有的平台
    List<String> selectAllPlatform();

    // 分页模糊查询
    Page<RawComment> selectPage2(Page<RawComment> page,
                                 @Param("workName") String workName,
                                 @Param("content") String content,
                                 @Param("country") String country,
                                 @Param("platform") String platform,
                                 @Param("postTime") String postTime);

    Page<RawComment> selectHotComments(Page<RawComment> page,
                                       @Param("workName") String workName,
                                       @Param("content") String content,
                                       @Param("country") String country,
                                       @Param("platform") String platform,
                                       @Param("postTime") String postTime);
   //根据国家分类查询作品的评论数量
    List<CountryCommentNum> getCommentNumByCountryAndWorkId(@Param("workId") Integer workId);

}
