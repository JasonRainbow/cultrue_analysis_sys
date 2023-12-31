package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.enums.PwdEnum;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.utils.AliOssUtil;
import com.example.demo.utils.JwtUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@Api(tags = "管理员控制器")
public class AdminController {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder; // crypt加密器

    @PostMapping("/login")
    @ApiOperation(value = "管理员登录", notes = "管理员登录的接口")
    public Result login(@RequestBody Admin adminParam) {
        Admin admin = adminMapper.selectByUsername(adminParam.getUsername()); // 通过管理员账号username查询
        if (admin == null) {
            return Result.error("-1", "管理员用户不存在！");
        }
        // 数据库中该管理员的的密码（密文）和输入的密码（明文）进行比较，
        if (!bCryptPasswordEncoder.matches(adminParam.getPassword(), admin.getPassword())) {
            return Result.error("-1", "密码错误！");
        }
        // 生成token
//        String token = JwtUtil.genToken(admin);
//        admin.setToken(token);
//        System.out.println(token);

        return Result.success(admin); // 返回成功
    }

    // 注册
    @PostMapping("/register")
    @ApiOperation(value = "管理员注册", notes = "管理员注册的接口")
    public Result register(@RequestBody Admin admin) {
        // 双冒号 lambda表达式
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, admin.getUsername()));
        if (res != null) {
            return Result.error("-1", "用户名重复");
        }
        String password = admin.getPassword();
        if (password == null || "".equals(password.trim())) password = PwdEnum.PASSWORD.getPassword(); // 如果密码为空，设置成默认的密码

        String name = admin.getName();
        if (name == null || "".equals(name.trim())) name = "管理员" + IdWorker.getId();
        Admin adminInfo = Admin.builder()
                .username(admin.getUsername())
                .password(bCryptPasswordEncoder.encode(password))
                .name(name) // 默认的名称
                .level(2)
                .build();

        adminMapper.insert(adminInfo);

        return Result.success();
    }

    // 保存（更新）管理员信息
    @ApiOperation(value = "修改管理员信息", notes = "修改管理员信息")
    @PutMapping("/update")
    public Result save(@RequestBody Admin admin) {
        /*if (admin.getPassword() == null) {
            admin.setPassword(bCryptPasswordEncoder.encode(PwdEnum.PASSWORD.getPassword()));
        }*/

        adminMapper.updateById(admin); // 通过主键id更新

        return Result.success();
    }

    // 重置管理员的密码
    @PutMapping("/resetPwd")
    @ApiOperation(value = "重置管理员密码", notes = "重置管理员密码的接口")
    public Result resetPwd(@RequestParam(name = "管理员ID") Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            return Result.error("-1", "未找到用户");
        }
        // 重置密码
        admin.setPassword(bCryptPasswordEncoder.encode(PwdEnum.PASSWORD.getPassword()));
        adminMapper.updateById(admin);
        return Result.success();
    }

    // 上传管理员的头像接口
    @PostMapping("/uploadAvatar")
    @ApiOperation(value = "上传管理员头像", notes = "上传管理员头像到OSS")
    public Result uploadAvatar(MultipartFile file) {
        if (!file.isEmpty()) {
//            Admin loginAdmin = JwtUtil.getLoginAdmin();
//            if (loginAdmin == null) {
//                return Result.error("401", "管理员未登录");
//            }
//            String avatar = AliOssUtil.upload("images/", file); // 上传文件到阿里云OSS存储服务器
//            loginAdmin.setAvatar(avatar);
//            int res = adminMapper.updateById(loginAdmin);
//            if (res > 0) {
//                return Result.success(avatar);
//            }
            return Result.error("-1", "上传管理员头像失败");
        }

        return Result.error("-1", "上传的文件为空");
    }

    // 更改管理员密码
    @PutMapping("/pass")
    @ApiOperation(value = "更改管理员密码", notes = "更改管理员密码的接口")
    public Result pass(@RequestBody Map<String, Object> map) {
//        Admin loginAdmin = JwtUtil.getLoginAdmin();
//        if (loginAdmin == null) {
//            return Result.error("-1", "未找到管理员");
//        }
//        String password = loginAdmin.getPassword(); // 获取管理员的密码
//        if (!JwtUtil.matchPassword(map.get("oldPwd").toString(), password)) {
//            return Result.error("-1", "修改密码失败，旧密码错误！");
//        }
//        if (JwtUtil.matchPassword(map.get("newPwd").toString(), password)) {
//            return Result.error("-1", "新密码不能与旧密码相同！");
//        }
//        map.put("newPwd", (bCryptPasswordEncoder.encode(map.get("newPwd").toString())));
//        map.put("id", loginAdmin.getId());
//        int res = adminMapper.updatePass(map);
//        if (res > 0) {
//            return Result.success();
//        }
        return Result.error("-1", "修改失败");
    }

    // 删除指定管理员
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "删除管理员", notes = "删除指定管理员的接口")
    @ApiImplicitParam(name = "ids", value = "管理员ID数组", type = "Integer[]")
    public Result delete(@PathVariable Integer[] ids) {
        int res = adminMapper.deleteBatchIds(Arrays.asList(ids));
        if (res > 0) {
            return Result.success();
        }
        return Result.error("-1", "删除失败！");
    }

    @GetMapping("/id/{id}") // 查询指定id的管理员
    @ApiOperation(value = "根据ID查询管理员", notes = "根据ID查询管理员的接口")
    @ApiImplicitParam(name = "id", value = "管理员ID", type = "Integer")
    public Result getById(@PathVariable Integer id) {
        return Result.success(adminMapper.selectById(id));
    }

    @GetMapping("/all") // 查询所有
    @ApiOperation(value = "查询所有管理员", notes = "查询所有管理员的接口")
    public Result findAll() {
        return Result.success(adminMapper.selectList(null));
    }

    // 获取当前登录的管理员的个人信息
    @GetMapping("/profile")
    @ApiOperation(value = "获取登录的管理员的个人信息", notes = "获取登录的管理员的个人信息的接口")
    public Result getProfile() {
//        String token = JwtUtil.getToken();
//        Admin loginAdmin = JwtUtil.getLoginAdmin();
//        if (loginAdmin == null) {
//            return Result.error("-1", "管理员不存在");
//        }
//        loginAdmin.setToken(token); // 设置token
//        return Result.success(loginAdmin);
        return null;
    }

    /**
     * 分页列表查询
      * @param pageNum 页号
     * @param pageSize 页大小
     * @param searchName 搜索关键词  昵称
     * @param searchUsername 搜索关键词  管理员用户名
     * @return 分页列表
     */
    @GetMapping("/byPage")
    @ApiOperation(value = "分页模糊查询所有管理员信息", notes = "分页查询所有管理员的接口")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String searchUsername,
                              @RequestParam(defaultValue = "") String searchName) {

        Page<Admin> adminPage = adminMapper.findByPage(new Page<>(pageNum, pageSize), searchUsername, searchName);

        return Result.success(adminPage);
    }

    /**
     * Excel导出
     *
     * @param response 响应对象
     * @throws IOException IO异常
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导出所有管理员信息", notes = "导出所有管理员信息的接口")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Admin> admins = adminMapper.selectList(null);
        for (Admin admin : admins) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("用户名", admin.getUsername());
            row1.put("昵称", admin.getName());
            row1.put("手机号码", admin.getPhone());
            row1.put("邮箱", admin.getEmail());
            list.add(row1);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", "UTF-8");
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
    @ApiOperation(value = "导出管理员信息导入模板", notes = "导出管理员信息模板的接口")
    public void importTemplate(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("用户名", "");
        row1.put("昵称", "");
        row1.put("手机号码", "");
        row1.put("邮箱", "");
        list.add(row1);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息导入模板", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * Excel导入  批量上传管理员信息 账号 昵称 分配默认的密码
     * 导入的模板可以使用 Excel导出的文件
     *
     * @param file Excel
     * @return json响应
     * @throws IOException IO异常
     */
    @PostMapping("/import")
    @ApiOperation(value = "导入管理员信息", notes = "管理员信息导入的接口")
    public Result upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<Admin> saveList = new ArrayList<>();
        for (List<Object> row : lists) { // 遍历每一行
            Admin admin = new Admin();
            admin.setUsername(row.get(0).toString());
            admin.setName(row.get(1).toString());
            admin.setPhone(row.get(2).toString());
            admin.setEmail(row.get(3).toString());
            admin.setPassword(PwdEnum.PASSWORD.getPassword()); // 默认的密码 123
            saveList.add(admin);
        }
        for (Admin admin : saveList) {
            adminMapper.insert(admin);
        }
        return Result.success();
    }
}
