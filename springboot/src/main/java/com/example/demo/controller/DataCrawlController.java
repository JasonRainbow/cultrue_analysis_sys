package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.DataCrawlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/crawler")
@Api(tags = "爬虫控制器")
public class DataCrawlController {
    @Autowired
    private DataCrawlService dataCrawlService;

    @GetMapping("/crawl-comments")
    @ApiOperation(value = "爬取关于指定作品的网络评论")
    public Result<?> crawlComments(@RequestParam Integer workId,
                                   @RequestParam String workName) throws UnsupportedEncodingException {
        boolean res = dataCrawlService.crawlComments(workId, workName);
        if (res) return Result.success();
        return Result.error("-1", "爬取评论失败，请稍后重试！");
    }
}
