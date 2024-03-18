package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.HotWork;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.dto.RequestHunanWorkDto;
import com.example.demo.entity.vo.WorkNumAndCommentNumVO;
import com.example.demo.entity.vo.WorkPlatformVO;

import java.util.List;

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

    /**
     * 分页查询同类型作品
     * @param workId 作品id
     * @param pageNum 页号
     * @param pageSize 页大小
     * @return 监测文化作品
     */
    Page<MonitorWork> querySameCategoryWork(Integer workId, Integer pageNum, Integer pageSize);

    /**
     * 查询所有的文学作品子类型，及子类型的作品数和总评论量
     * @return 列表
     */
    List<WorkNumAndCommentNumVO> queryAllBookCategories();

    /**
     * 查询所有的影视作品子类型，及子类型的作品数和总评论量
     * @return 列表
     */
    List<WorkNumAndCommentNumVO> queryAllVideoCategories();

    /**
     * 查询所有的地域，及属于该地域的作品数和总评论量
     * @return 列表
     */
    List<WorkNumAndCommentNumVO> queryAllOrigins();

    /**
     * 根据用户ID检索其监测作品列表
     * @param userId 用户ID
     * @return 监测作品列表
     */
    List<MonitorWork> queryWorksByUserId(Integer userId);

}
