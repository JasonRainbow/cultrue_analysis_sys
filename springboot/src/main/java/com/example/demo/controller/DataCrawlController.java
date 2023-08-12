package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.DataAnalysisService;
import com.example.demo.service.DataCrawlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @Value("${scrap_host}")
    private String scrap_hots;

    @GetMapping("/crawl-comments")
    @ApiOperation(value = "爬取关于指定作品的网络评论")
    public Result<?> crawlComments(@RequestParam Integer workId,
                                   @RequestParam String keyword,
                                   @RequestParam String platform) {
        String baseUri = scrap_hots + "";
        switch (platform) {
            case "豆瓣":
                baseUri += "/scrap_douban";
                break;
            case "Twitter":
                baseUri += "/scrap_twitter";
                break;
            case "Facebook":
                baseUri += "/scrap_facebook";
                break;
            case "Youtube":
                baseUri += "/scrap_youtube";
                break;
            case "烂番茄":
                baseUri += "/scrap_tomato";
                break;
            case "GoodReads":
                baseUri += "/scrap_goodreads";
                break;
            case "IMDb":
                baseUri += "/scrap_imdb";
                break;
            case "亚马逊":
                baseUri += "/scrap_amazon";
                break;
            default:
                return Result.error("-1", "没有平台参数");
        }
        boolean res = dataCrawlService.crawlComments(workId, keyword, baseUri, platform);
        if (res) return Result.success();
        return Result.error("-1", "爬取评论失败，请稍后重试！");
    }

    @GetMapping("/crawl-scores")
    @ApiOperation(value = "爬取关于指定作品的网络平台得分")
    public Result<?> crawlScores(@RequestParam Integer workId,
                                 @RequestParam String keyword,
                                 @RequestParam String platform,
                                 @RequestParam(defaultValue = "1", required = false) Integer fromNet) {
        String baseUri = scrap_hots + "";
        switch (platform) {
            case "豆瓣":
                baseUri += "/scrap_douban_score";
                break;
            case "烂番茄":
                baseUri += "/scrap_tomato_score";
                break;
            case "GoodReads":
                baseUri += "/scrap_goodreads_score";
                break;
            case "IMDb":
                baseUri += "/scrap_IMDb_score";
                break;
            case "Amazon":
                baseUri += "/scrap_amazon_score";
                break;
            case "Twitter":
            case "Facebook":
            case "Youtube":
                boolean res = dataAnalysisService.generatePlatformScore(workId, platform);
                if (res) {
                    return Result.success();
                } else {
                    return Result.error("-1", "计算平台得分失败");
                }
            default:
                return Result.error("-1", "没有平台参数或平台不正确");
        }
        boolean res;
        if (fromNet == 1) {
            res = dataCrawlService.crawlScores(workId, keyword, baseUri, platform);
        } else {
            res = dataAnalysisService.generatePlatformScore(workId, platform);
        }

        if (res) return Result.success();
        return Result.error("-1", "爬取评分数据失败，请稍后重试！");
    }
}
