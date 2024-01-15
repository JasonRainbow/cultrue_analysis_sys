package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.HotWork;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.dto.RequestHunanWorkDto;
import com.example.demo.entity.vo.WorkPlatformVO;
import com.example.demo.mapper.HotWorkMapper;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.PlatformMapper;
import com.example.demo.service.MonitorWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MonitorWorkServiceImpl implements MonitorWorkService {

    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    @Autowired
    private HotWorkMapper hotWorkMapper;

    @Autowired
    private PlatformMapper platformMapper;

    @Override
    public Page<WorkPlatformVO> queryWorksbyPage(Integer pageNum, Integer pageSize,
                                                 String searchName, String searchCategory) {
        LambdaQueryWrapper<MonitorWork> queryWrapper = Wrappers.<MonitorWork>lambdaQuery()
                .like(MonitorWork::getName, searchName)
                .like(MonitorWork::getCategory, searchCategory)
                .orderByAsc(MonitorWork::getId);
        Page<MonitorWork> workPage = monitorWorkMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);

        return generateNewPage(workPage);
    }

    @Override
    public Page<WorkPlatformVO> queryHunanWorks(RequestHunanWorkDto requestBody) {

        String subCategories = null;
        String origins = null;
        // 提取成逗号分隔的字符串
        if (requestBody.getSubCategories() != null) {
            subCategories = requestBody.getSubCategories().stream().map((String e)->"\"" + e + "\"")
                    .collect(Collectors.toList()).toString();
            subCategories = subCategories.replace("[", "").replace("]", "");
        }
        if (requestBody.getOrigins() != null) {
            origins = requestBody.getOrigins().stream().map((String e)->"\"" + e + "\"")
                    .collect(Collectors.toList()).toString();
            origins = origins.replace("[", "").replace("]", "");
        }
        Page<MonitorWork> workPage = monitorWorkMapper.selectHunanWork(
                new Page<>(requestBody.getPageNum(), requestBody.getPageSize()),
                subCategories,
                origins);

        return generateNewPage(workPage);
    }

    @Override
    public Page<HotWork> queryHotWorks(Integer pageNum, Integer pageSize, String searchName, String searchCategory) {
        LambdaQueryWrapper<HotWork> queryWrapper = Wrappers.<HotWork>lambdaQuery()
                .eq(HotWork::getIsHotWork,1)
                .like(HotWork::getName, searchName)
                .like(HotWork::getCategory, searchCategory)
                .orderByAsc(HotWork::getId);
        Page<HotWork> hotWorkPage = hotWorkMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);

        for (HotWork hotWork: hotWorkPage.getRecords()) {
            hotWork.setPlatform(platformMapper.selectById(hotWork.getSourcePlatform()));
        }

        return hotWorkPage;
    }

    private Page<WorkPlatformVO> generateNewPage(Page<MonitorWork> workPage) {
        // 构造WorkPlatformVO页存储视图信息
        Page<WorkPlatformVO> workPlatformVOPage = new Page<>();
        workPlatformVOPage.setRecords(new ArrayList<>());
        workPlatformVOPage.setTotal(workPage.getTotal());
        workPlatformVOPage.setCurrent(workPage.getCurrent());
        workPlatformVOPage.setSize(workPage.getSize());
        for (MonitorWork work: workPage.getRecords()) {
            workPlatformVOPage.getRecords().add(new WorkPlatformVO(work,
                    platformMapper.selectById(work.getSourcePlatform())));
        }

        return workPlatformVOPage;
    }
}
