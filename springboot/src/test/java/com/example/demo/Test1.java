package com.example.demo;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.LoginDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Test1 {

    public static void main(String[] args) {
        /*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);*/
        /*System.out.println(encoder.matches("123456", "" +
                "$2a$10$ioF9GdWF6ahRStp8Tj4iiOeKe1im/jaaHbk0rRiYIN6wb838EVWay"));*/
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(dateFormat.format(new Date()));
//        String s = "1 2 3  4 5 6 7  7 9  20";
//        String[] split = s.split("\\s+");
//        System.out.println(Arrays.toString(split));
//        Result success = Result.success(new User());
//        success.put("name", "hzx").put("password", "123456");
//        System.out.println(success);
        Object loginDto = JSON.parseObject("{\"username\":\"hzx\",\"password\":\"123456\"}", Object.class, JSONReader.Feature.SupportAutoType);
        System.out.println((LoginDto)loginDto);
    }
}
