package com.example.demo;

import com.example.demo.service.SysRoleService;
import com.example.demo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class SysServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    private static final Logger logger = Logger.getGlobal();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void testGetRolesByUsername() {
        logger.log(Level.INFO, sysRoleService.getRolesByUsername("root").toString());
    }

    @Test
    public void test2() {
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void testJwtUtil() {
        String token = JwtUtil.createJWT("hzx");
        System.out.println(token);
        char[] chars = token.toCharArray();
        chars[0] = '1';
        chars[3] = '3';
        System.out.println(JwtUtil.isVerifiedToken(token));
        System.out.println(JwtUtil.isVerifiedToken(new String(chars)));
    }

    @Test
    public void testVerifyExpired() throws Exception {
        boolean res = JwtUtil.verifyExpired("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmODQ2ZTMwZjU0ZDk0NDQ0YWUwMGQwOTAwZTgxM2I0NiIsInN1YiI6InJvb3QiLCJpc3MiOiJoengiLCJpYXQiOjE2OTY2OTk0OTIsImV4cCI6MTY5NzA0NTA5Mn0.MMJZFMuynGBIczdmEObSVD_GkGeUZ1PduvN_2G8QG5A");
        System.out.println(res);
    }
}
