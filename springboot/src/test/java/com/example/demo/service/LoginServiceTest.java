package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.common.Result;
import com.example.demo.entity.dto.LoginDto;
import com.example.demo.enums.ResponseStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    /**
     * 测试登录服务
     */
    @Test
    public void testDoLogin() {
        /*LoginDto loginDto = new LoginDto();
        loginDto.setUsername("hzx");
        loginDto.setPassword("123456");
        Result result = loginService.doLogin(loginDto, false);
        System.out.println(result);
        assertEquals(ResponseStatusEnum.LOGIN_SUCCESS.getMsg(), result.getMsg());

        loginDto.setPassword("123");
        try {
            result = loginService.doLogin(loginDto, false);
        } catch (Exception e) {
            assertEquals(ResponseStatusEnum.USERNAME_PASSWORD_ERROR.getMsg(), e.getMessage());
        }

        loginDto.setPassword("123456");
        try {
            result = loginService.doLogin(loginDto, true);
        } catch (Exception e) {
            assertEquals(ResponseStatusEnum.LOGIN_NOT_ADMIN.getMsg(), e.getMessage());
        }

        loginDto.setUsername("root");
        loginDto.setPassword("123456");
        // 管理员登录
        result = loginService.doLogin(loginDto, true);
        assertEquals(ResponseStatusEnum.LOGIN_SUCCESS.getMsg(), result.getMsg());*/
    }

}
