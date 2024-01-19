package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.CommentPlatformDto;
import com.example.demo.entity.RawComment;
import com.example.demo.entity.vo.CountryCommentNum;
import com.example.demo.entity.vo.MonthCommentNum;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.util.Pair;

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

    /**
     * 分页查询不同语言评论的数量
     * @param page 分页对象
     * @param language 语言
     * @return 语言->评论数量
     */
    Page<Pair<String, Integer>> selectCommentSumByLanguage(Page<Pair<String, Integer>> page,
                                                           @Param("language") String language);

    /**
     * 分页查询不同评论的评论数量
     * @param page 分页对象
     * @param platform 平台名称
     * @return 平台->评论数量
     */
    Page<CommentPlatformDto> selectCommentSumByPlatform(Page<CommentPlatformDto> page,
                                                        @Param("platform") String platform);

    /**
     * 分页查询不同子类型作品的评论数量
     * @param page 分页对象
     * @param subCategory 作品子类型
     * @return 作品子类型->评论数量
     */
    Page<Pair<String, Integer>> selectCommentSumBySubCategory(Page<CommentPlatformDto> page,
                                                              @Param("subCategory") String subCategory);

    /**
     * 查询不同子类型作品的评论数量 查询所有
     * @param subCategory 作品子类型
     * @return 作品子类型->评论数量 列表
     */
    List<Pair<String, Integer>> selectCommentSumBySubCategory(@Param("subCategory") String subCategory);

    List<String> selectPlatformByWorkId(@Param("workId") Integer workId);

    MonthCommentNum getCommentNumByWorkIdAndYear(@Param("workId") Integer workId, @Param("month") int i, @Param("country") String country);

    List<String> getPlatformByWorkIdAndCountry(@Param("workId") Integer workId, @Param("country") String country);

    Integer selectCommentCountBySubCategoryAndOrigin(String subCategory, String origin, Boolean hunanOnly);

}
