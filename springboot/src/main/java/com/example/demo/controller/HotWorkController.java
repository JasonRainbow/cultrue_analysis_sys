package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.HotWork;
import com.example.demo.entity.PolarityAnalysis;
import com.example.demo.mapper.HotWorkMapper;
import com.example.demo.mapper.PolarityAnalysisMapper;
import com.example.demo.service.MonitorWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// 热点文化作品信息
@RequestMapping("/api/hot-work")
@RestController
@Api(tags = "热点文化作品控制器")
public class HotWorkController {
    @Autowired
    private HotWorkMapper hotWorkMapper;

    @Autowired
    private PolarityAnalysisMapper polarityAnalysisMapper;

    @Autowired
    private MonitorWorkService monitorWorkService;

    // 查询所有的热点作品信息
    @GetMapping("/all")
    @ApiOperation(value = "查询所有热点文化作品信息")
    public Result findAll() {
        LambdaQueryWrapper<HotWork> queryWrapper = Wrappers.<HotWork>lambdaQuery()
                .eq(HotWork::getIsHotWork, 1);
        List<HotWork> hotWorks = hotWorkMapper.selectList(queryWrapper);
        return Result.success(hotWorks);
    }

    @GetMapping("/id/{id}")
    @ApiOperation(value = "根据ID查询热点文化作品信息")
    public Result findById(@PathVariable Long id) {
        HotWork hotWork = hotWorkMapper.selectById(id);
        return Result.success(hotWork);
    }

    // 分页 搜索查询
    @GetMapping("/byPage")
    @ApiOperation(value = "分页查询热点文化作品信息")
    public Result findPage(@RequestParam(required = false, defaultValue = "") String searchName,
                              @RequestParam(required = false, defaultValue = "") String searchCategory,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<HotWork> queryWrapper = Wrappers.<HotWork>lambdaQuery()
                .eq(HotWork::getIsHotWork,1)
                .like(HotWork::getName, searchName)
                .like(HotWork::getCategory, searchCategory)
                .orderByAsc(HotWork::getId);
        return Result.success(hotWorkMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 分页 搜索查询
    @GetMapping("/byPage2")
    @ApiOperation(value = "分页查询热点文化作品信息带来源平台")
    public Result findPage2(@RequestParam(required = false, defaultValue = "") String searchName,
                           @RequestParam(required = false, defaultValue = "") String searchCategory,
                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        return Result.success(monitorWorkService.queryHotWorks(pageNum, pageSize, searchName, searchCategory));
    }

    // 根据id删除指定热点作品
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "根据ID删除热点文化作品")
    @ApiImplicitParam(name = "ids", value = "热点文化作品ID数组")
    public Result deleteById(@PathVariable Long[] ids) {
        StringBuilder idList = new StringBuilder("(");
        for (Long id: ids) {
            idList.append(id).append(",");
        }
        idList.setCharAt(idList.length() - 1, ')');
        boolean res = hotWorkMapper.deleteByIds(idList.toString());
        if (res) {
            return Result.success();
        }
        return Result.error("-1", "该热点作品已经被删除了");
    }

    // 更新热点文化作品
    @GetMapping("/updateHotWork")
    @ApiOperation(value = "更新热点文化作品")
    public Result updateHotWorks(@RequestParam(required = false, defaultValue = "10") Integer maxNum) {
        List<PolarityAnalysis> worksPolarity = polarityAnalysisMapper.selectWorksPolarity();
        Map<Integer, Double> workEffects = new HashMap<>();
        int cnt = 0;
        for(PolarityAnalysis item: worksPolarity) {
            workEffects.put(item.getWorkId(), item.getPositive() * 1.0 / (
                    item.getPositive() + item.getNegative() + item.getNeutrality()
                    ));
        }
        List<Map.Entry<Integer, Double>> entries = new ArrayList<>(workEffects.entrySet());
        // 以得分值对列表排序
        entries.sort((Map.Entry<Integer, Double> x1, Map.Entry<Integer, Double> x2)
                -> x1.getValue() < x2.getValue() ? 1: -1);
        hotWorkMapper.deleteAllHotWorks();
        // 更新监测作品的热点信息和传播效果得分
        for(Map.Entry<Integer, Double> item: entries) {
            cnt++;
            if (cnt <= maxNum) {
                hotWorkMapper.updateIsHotWork(item.getKey());
            }
            hotWorkMapper.updateEffectScore(item.getKey(), item.getValue());
        }

        return Result.success();
    }

    /*// 新增热点文化作品
    @PostMapping("/add")
    @ApiOperation(value = "新增热点文化作品信息")
    public Result<?> add(@RequestBody HotWork hotWork) {
        int res = hotWorkMapper.insert(hotWork);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "插入热点作品失败");
    }*/

    /*// 修改热点文化作品信息
    @PutMapping("/update")
    @ApiOperation(value = "修改热点文化作品信息")
    public Result<?> update(@RequestBody HotWork hotWork) {
        int res = hotWorkMapper.updateById(hotWork);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "修改热点文化作品失败");
    }*/

    /**
     * Excel导出
     *
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出热点文化作品信息")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<HotWork> all = hotWorkMapper.selectList(null);
        for (HotWork hotWork : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("热点文化作品名称", hotWork.getName());
            row.put("作品类型", hotWork.getCategory());
            row.put("作品介绍内容", hotWork.getContent());
            row.put("作品介绍网址", hotWork.getCiteUrl());
            row.put("作品介绍图片url", hotWork.getImgUrl());
            row.put("作品报道时间", hotWork.getPostTime());
            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("热点文化作品介绍信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * Excel导出  模板
     *
     * @param response 响应对象
     * @throws IOException IO异常
     */
    @RequestMapping(value = "/importTemplate", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出热点文化作品信息导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("热点文化作品名称", "");
        row.put("作品类型", "");
        row.put("作品介绍内容", "");
        row.put("作品介绍网址", "");
        row.put("作品介绍图片url", "");
        row.put("作品报道时间", "");
        list.add(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("热点文化作品介绍信息导入模板", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * Excel导入
     *
     * @param file Excel
     * @return
     * @throws IOException
     */
    @PostMapping("/import")
    @ApiOperation(value = "导入热点文化作品信息")
    public Result upload(MultipartFile file) throws IOException, ParseException {
        InputStream inputStream = file.getInputStream();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取excel表中的每一行数据
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<HotWork> saveList = new ArrayList<>();
        for (List<Object> row : lists) { // 将数据保存到列表中
            HotWork hotWork = new HotWork();
            hotWork.setName(row.get(0).toString());
            hotWork.setCategory(row.get(1).toString());
            hotWork.setContent(row.get(2).toString());
            hotWork.setCiteUrl(row.get(3).toString());
            hotWork.setImgUrl(row.get(4).toString());
            hotWork.setPostTime(sdf.parse(row.get(5).toString()));
            saveList.add(hotWork);
        }
        for (HotWork hotWork : saveList) {
            hotWorkMapper.insert(hotWork); // 插入数据
        }
        return Result.success();
    }
}
