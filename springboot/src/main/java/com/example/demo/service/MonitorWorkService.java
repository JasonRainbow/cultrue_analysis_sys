package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.HotWork;
import com.example.demo.entity.dto.RequestHunanWorkDto;
import com.example.demo.entity.vo.WorkPlatformVO;

public interface MonitorWorkService {

    /**
     * 分页查询作品
     * @param pageNum 页号
     * @param pageSize 页大小
     * @param searchName 搜索作品名
     * @param searchCategory 搜索作品类别
     * @return 一页作品信息
     */
    Page<WorkPlatformVO> queryWorksbyPage(Integer pageNum, Integer pageSize,
                                          String searchName, String searchCategory);


    /**
     * 分页多条件查询湖湘文化作品
     * @param requestBody 请求体
     * @return 文化作品
     */
    Page<WorkPlatformVO> queryHunanWorks(RequestHunanWorkDto requestBody);

    /**
     * 分页查询热点文化作品
     * @param pageNum 页号
     * @param pageSize 页大小
     * @param searchName 搜索作品名称
     * @param searchCategory 搜索作品类别
     * @return 热点文化作品
     */
    Page<HotWork> queryHotWorks(Integer pageNum, Integer pageSize,
                                String searchName, String searchCategory);
}
