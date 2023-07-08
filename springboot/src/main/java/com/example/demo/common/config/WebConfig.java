package com.example.demo.common.config;

import com.example.demo.common.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 拦截器配置
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置拦截规则与注入拦截器
     * @param registry xx
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor())
//                .addPathPatterns("/**/update", "/**/add", "/**/delete/**",
//                        "/**/import", "/**/export", "/**/importTemplate") // 拦截请求
                // 指定的请求拦截的释放
                /*.excludePathPatterns("/api/user/login", "/api/user/register",
                        "/api/admin/login", "/api/admin/register",
                        "/api/hot-work/**",
                        "/imserver/**", "/files/**", "/alipay/**",
                        "/doc.html", "/webjars/**", "/swagger-resources/**")*/;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置拦截器访问静态资源
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}
