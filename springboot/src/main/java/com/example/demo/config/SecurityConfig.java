package com.example.demo.config;

import com.example.demo.config.authentication.*;
import com.example.demo.constant.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hzx
 * @version 1.0
 * @Description Spring security的配置类
 * @Date 2021/12/3 21:35
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyOncePerRequestFilter myOncePerRequestFilter;

    @Autowired
    private UserDetailsService userDetailsService; // 获取用户的详细信息

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint; // 未登录

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler; // 无权访问

    @Resource
    private ConfigProperties configProperties;

    @Resource
    private Environment environment;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAll = configProperties.getPermitAll();
        String[] arr = new String[0];
        http
                // 设置不需要认证的URL
                .authorizeRequests()
                // 允许未登录的用户进行访问  都可以访问 匿名和认证  如果用anonymous()则必须匿名，不能认证访问
                .antMatchers(permitAll.toArray(arr)).permitAll()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/*.js", "/favicon.ico").permitAll()
                .antMatchers("/webjars/**", "/*/api-docs", "/swagger-resources/**").permitAll()
                // 其余的url都要认证才能访问
                .anyRequest().authenticated()
                // 关闭csrf跨域
                .and().csrf().disable()
                // 关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        // 在UsernamePasswordAuthenticationFilter前添加认证过滤器
        http.addFilterBefore(myOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // 异常处理
        http
                .exceptionHandling()
                // 认证失败处理器
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                // 权限不足处理器
                .accessDeniedHandler(myAccessDeniedHandler);

        // 允许跨域
        http.cors();
    }

    /**
     * 身份认证接口
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /*
     * 注入BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
