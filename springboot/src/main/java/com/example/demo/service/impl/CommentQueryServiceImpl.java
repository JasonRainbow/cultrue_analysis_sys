package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.CommentPlatformDto;
import com.example.demo.mapper.RawCommentMapper;
import com.example.demo.service.CommentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentQueryServiceImpl implements CommentQueryService {

    @Autowired
    private RawCommentMapper rawCommentMapper;

    @Override
    public Page<Pair<String, Integer>> querySubCategoryCommentNum(String subCategory, Integer pageNum, Integer pageSize) {

        return rawCommentMapper.selectCommentSumBySubCategory(new Page<>(pageNum, pageSize), subCategory);
    }

    @Override
    public Page<Pair<String, Integer>> queryLanguageCommentNum(String language, Integer pageNum, Integer pageSize) {


        return rawCommentMapper.selectCommentSumByLanguage(new Page<>(pageNum, pageSize), language);
    }

    @Override
    public Page<CommentPlatformDto> queryPlatformCommentNum(String platform, Integer pageNum, Integer pageSize) {
        int total = rawCommentMapper.selectCount(new QueryWrapper<>());
        Page<CommentPlatformDto> res = rawCommentMapper.selectCommentSumByPlatform(
                new Page<>(pageNum, pageSize), platform);
        List<CommentPlatformDto> records = res.getRecords();
        for (CommentPlatformDto record : records) {
            record.setProportion(Math.round(record.getCount() * 1.0 / total * 1000) / 10.0);
        }

        return res;
    }
}
