package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hzx
 * @version 1.0
 * @Description Spring security的配置类
 * @Date 2021/12/3 21:35
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 授权
                .antMatchers("/**")
                .anonymous()
                .anyRequest()
                // 权限认证
                .authenticated()
                .and().csrf().disable(); // 跨域请求验证

    }

    /*
     * 注入BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public static void main(String[] args) {
//        String encode = new BCryptPasswordEncoder().encode("admin");
//        System.out.println(encode);
//    }
}
