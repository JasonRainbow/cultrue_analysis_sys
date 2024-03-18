package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.MonitorRequest;
import com.example.demo.entity.RawComment;
import com.example.demo.entity.WorkCommentNum;
import com.example.demo.entity.dto.CommentPlatformDto;
import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.dto.RequestHunanWorkDto;
import com.example.demo.entity.vo.RecommendWorkVO;
import com.example.demo.mapper.MonitorRequestMapper;
import com.example.demo.mapper.MonitorWorkMapper;
import com.example.demo.mapper.PlatformMapper;
import com.example.demo.mapper.RawCommentMapper;
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
import java.util.*;
import java.util.stream.Collectors;

// 监测文化作品信息
@RequestMapping("/api/monitor-work")
@RestController
@Api(tags = "监测作品控制器")
public class MonitorWorkController {
    @Autowired
    private MonitorWorkMapper monitorWorkMapper;

    @Autowired
    private RawCommentMapper rawCommentMapper;

    @Autowired
    private PlatformMapper platformMapper;

    @Autowired
    private MonitorRequestMapper monitorRequestMapper;

    @Autowired
    private MonitorWorkService monitorWorkService;

    // 查询所有的监测作品信息
    @GetMapping("/all")
    @ApiOperation(value = "查询所有监测作品信息")
    public Result findAll() {
        List<MonitorWork> monitorWorks = monitorWorkMapper.selectList(null);
        return Result.success(monitorWorks);
    }

    // 根据作品id查询指定的文化作品
    @GetMapping("/id/{id}")
    @ApiOperation(value = "根据ID查询指定文化作品信息")
    public Result findById(@PathVariable Long id) {
        MonitorWork monitorWork = monitorWorkMapper.selectById(id);
        //设置作品所属平台信息
        monitorWork.setPlatform(platformMapper.selectById(monitorWork.getSourcePlatform()));
        //设置作品评论总数
        QueryWrapper<RawComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workId", monitorWork.getId());
        monitorWork.setCommentCnt(rawCommentMapper.selectCount(queryWrapper));
        //设置作品评论点赞总数
        monitorWork.setCommentLikesCnt(rawCommentMapper.getWorkCommentLikesNumByWorkId(monitorWork.getId()));
        return Result.success(monitorWork);
    }

    // 分页 搜索查询
    @GetMapping("/byPage")
    @ApiOperation(value = "分页查询监测作品信息")
    public Result findPage(@RequestParam(required = false, defaultValue = "") String searchName,
                              @RequestParam(required = false, defaultValue = "") String searchCategory,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<MonitorWork> queryWrapper = Wrappers.<MonitorWork>lambdaQuery()
                .like(MonitorWork::getName, searchName)
                .like(MonitorWork::getCategory, searchCategory)
                .orderByAsc(MonitorWork::getId);
        return Result.success(monitorWorkMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 分页 搜索查询
    @GetMapping("/byPage2")
    @ApiOperation(value = "分页查询监测作品信息带来源平台信息")
    public Result findPage2(@RequestParam(required = false, defaultValue = "") String searchName,
                           @RequestParam(required = false, defaultValue = "") String searchCategory,
                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        return Result.success(monitorWorkService.queryWorksbyPage(pageNum, pageSize, searchName, searchCategory));
    }
    // 分页 根据作品id查询同类型其他作品传播效果得分
    @GetMapping("/getSameCategoryWork")
    @ApiOperation(value = "分页查询同类型作品传播效果得分")
    public Result getSameCategoryByPageAndWorkId(@RequestParam Integer workId,
                                                 @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        return Result.success(monitorWorkService.querySameCategoryWork(workId, pageNum, pageSize));
    }

    // 根据id删除指定监测作品
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "根据ID删除指定监测作品")
    @ApiImplicitParam(name = "ids", value = "监测作品ID数组")
    public Result deleteById(@PathVariable Long[] ids) {
        int res = monitorWorkMapper.deleteBatchIds(Arrays.asList(ids));
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "该监测作品已经被删除了");
    }

    // 新增监测文化作品
    @PostMapping("/add")
    @ApiOperation(value = "新增监测作品")
    public Result add(@RequestBody MonitorWork monitorWork) {
        monitorWork.setCreateTime(new Date());
        int res = monitorWorkMapper.insert(monitorWork);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "插入监测作品失败");
    }

    // 修改监测作品信息
    @PutMapping("/update")
    @ApiOperation(value = "修改监测作品信息")
    public Result update(@RequestBody MonitorWork monitorWork) {
        int res = monitorWorkMapper.updateById(monitorWork);
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "修改监测文化作品失败");
    }

    // 通过用户的id获取用户申请的监测作品信息
    @GetMapping("/byUserId")
    @ApiOperation(value = "查询用户申请的监测作品信息")
    public Result findAllByUserId(@RequestParam(required = false, defaultValue = "-1") Integer userId) {

        return Result.success(monitorWorkService.queryWorksByUserId(userId));
    }

    // 通过用户的id获取用户申请的监测作品信息  分页查询
    @GetMapping("/byUserIdPaging")
    @ApiOperation(value = "查询用户申请的监测作品信息（分页）")
    public Result findAllByUserIdPaging(@RequestParam(required = false, defaultValue = "-1") Integer userId,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<MonitorWork> monitorWorks = monitorWorkMapper.selectByUserIdPaging(new Page<>(pageNum, pageSize), userId);
        return Result.success(monitorWorks);
    }

    // 获取指定文化作品的平台来源统计信息
    @GetMapping("/countPlatform")
    @ApiOperation(value = "根据作品ID查询平台来源统计信息")
    public Result countPlatform(@RequestParam Integer workId) {
        List<CommentPlatformDto> platformDtos = rawCommentMapper.countPlatformByWorkId(workId);
        return Result.success(platformDtos);
    }

    // 获取指定用户的推荐作品
    @GetMapping("/recommendByUserId")
    @ApiOperation(value = "查询指定用户的推荐作品")
    public Result recommendByUserId(@RequestParam Integer userId) {
//        System.out.println(userId);
        List<RecommendWorkVO> recommendWorkVOS = monitorWorkMapper.selectRecommendWorksByUserId(userId);

        // 如果没有给用户推荐的作品，就使用给管理员推荐的作品
        if (recommendWorkVOS.isEmpty()) {
            recommendWorkVOS = monitorWorkMapper.selectRecommendWorksByUserId(1);
        }

        for (RecommendWorkVO recommendWorkVO : recommendWorkVOS) {
            QueryWrapper<MonitorRequest> query1 = new QueryWrapper<>();
            query1.eq("userId", userId);
            query1.eq("workId", recommendWorkVO.getWorkId());
            List<MonitorRequest> monitorRequests = monitorRequestMapper.selectList(query1);
            recommendWorkVO.setIsMonitor(!monitorRequests.isEmpty());
        }

        return Result.success(recommendWorkVOS);
    }

    // 获取影视类型的所有子类型
    @GetMapping("/getAllVideoCategories")
    @ApiOperation(value = "获取影视类型的所有子类型")
    public Result getAllVideoCategories() {
        return Result.success(monitorWorkService.queryAllVideoCategories());
    }

    // 获取文学类型的所有子类型
    @GetMapping("/getAllBookCategories")
    @ApiOperation(value = "获取文学类型的所有子类型")
    public Result getAllBookCategories() {
        return Result.success(monitorWorkService.queryAllBookCategories());
    }

    // 获取所有的地域
    @GetMapping("/getAllOrigin")
    @ApiOperation(value = "获取所有的地域")
    public Result getAllOrigin() {
        return Result.success(monitorWorkService.queryAllOrigins());
    }

    // 多条件查询湖南的文化作品
    @PostMapping("/getHunanWork")
    @ApiOperation(value = "多条件查询湖南的文化作品")
    public Result getHunanWorkByPage(@RequestBody RequestHunanWorkDto requestBody) {

        return Result.success(monitorWorkService.queryHunanWorks(requestBody));
    }

    /**
     * Excel导出
     *
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出所有监测作品信息")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<MonitorWork> all = monitorWorkMapper.selectList(null);
        for (MonitorWork monitorWork : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("热点文化作品名称", monitorWork.getName());
            row.put("作品类型", monitorWork.getName());
            row.put("作品监测创建时间", monitorWork.getCreateTime());
            row.put("作品监测完成时间", monitorWork.getEndTime());
            row.put("是否完成评论数据爬取", monitorWork.getCrawlOk());
            row.put("是否完成情感分析", monitorWork.getSentimentOk());
            row.put("是否完成情感极性分析", monitorWork.getPolarityOk());
            row.put("是否完成词频统计", monitorWork.getWordFreqOk());
            row.put("是否全部完成", monitorWork.getAllDone());
            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("监测文化作品信息", "UTF-8");
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
    @ApiOperation(value = "导出监测作品信息导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("热点文化作品名称", "");
        row.put("作品类型", "");
        list.add(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("监测文化作品信息导入模板", "UTF-8");
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
    @ApiOperation(value = "导入监测作品信息")
    public Result upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        // 获取excel表中的每一行数据
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<MonitorWork> saveList = new ArrayList<>();
        for (List<Object> row : lists) { // 将数据保存到列表中
            MonitorWork monitorWork = new MonitorWork();
            monitorWork.setName(row.get(0).toString());
            monitorWork.setCategory(row.get(1).toString());
            saveList.add(monitorWork);
        }
        for (MonitorWork monitorWork : saveList) {
            monitorWorkMapper.insert(monitorWork); // 插入数据
        }
        return Result.success();
    }

    @GetMapping("/getWorkByCategory")
    @ApiOperation(value = "分类查询所有作品")
    public Result getWorkByCategory(){
        List<String> categories = monitorWorkMapper.getAllCategory();
        Map<String, Object> categoryMap = new HashMap<>();
        for(String category:categories){
            List<String> subCategory = monitorWorkMapper.getAllSubCategory(category);
            Map<String, Object> workMap = new HashMap<>();
            for(String sub : subCategory){
                LambdaQueryWrapper<MonitorWork> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(MonitorWork::getSubCategory, sub);
                List<MonitorWork> workList = monitorWorkMapper.selectList(lambdaQueryWrapper);
                workMap.put(sub, workList);
            }
            categoryMap.put(category, workMap);
        }
        return Result.success(categoryMap);
    }

    @GetMapping("/getWorkAndCommentNumByCategory")
    @ApiOperation(value = "分类查询所有作品以及评论数量")
    public Result getWorkAndCommentNumByCategory(){
        List<String> categories = monitorWorkMapper.getAllCategory();
        Map<String, Object> categoryMap = new HashMap<>();
        for(String category:categories){
            List<String> subCategory = monitorWorkMapper.getAllSubCategory(category);
            Map<String, Object> workMap = new HashMap<>();
            for(String sub : subCategory){
                LambdaQueryWrapper<MonitorWork> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(MonitorWork::getSubCategory, sub);
                List<MonitorWork> workList = monitorWorkMapper.selectList(lambdaQueryWrapper);
                List<WorkCommentNum> commentNumList = new ArrayList<>();
                for(MonitorWork work : workList){
                    QueryWrapper<RawComment>  queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("workId",work.getId());
                    int count = rawCommentMapper.selectCount(queryWrapper);
                    commentNumList.add(new WorkCommentNum(work.getId(),work.getName(),count));
                }
                workMap.put(sub, commentNumList);
            }
            categoryMap.put(category, workMap);
        }
        return Result.success(categoryMap);
    }

}
