package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.CommentPlatformDto;
import org.springframework.data.util.Pair;

/**
 * @author hzx
 */
public interface CommentQueryService {

    /**
     * 查询子类型作品的评论量
     * @param subCategory 子类型 可以为空
     * @param pageNum 页号
     * @param pageSize 页大小
     * @return <作品子类型, 评论量> 列表
     */
    Page<Pair<String, Integer>> querySubCategoryCommentNum(String subCategory, Integer pageNum, Integer pageSize);

    /**
     * 查询不同语言的评论量
     * @param language 语言
     * @param pageNum 页号
     * @param pageSize 页大小
     * @return <语言, 评论量> 列表
     */
    Page<Pair<String, Integer>> queryLanguageCommentNum(String language, Integer pageNum, Integer pageSize);

    /**
     * 查询不同平台的评论量
     * @param platform 平台名称
     * @param pageNum 页号
     * @param pageSize 页大小
     * @return <平台, 评论量> 列表
     */
    Page<CommentPlatformDto> queryPlatformCommentNum(String platform, Integer pageNum, Integer pageSize);
}
