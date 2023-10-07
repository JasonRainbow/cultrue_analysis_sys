package com.example.demo.config;

import com.example.demo.common.AuthInterceptor;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
//        registry.addInterceptor(authInterceptor()).addPathPatterns("/**")
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

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许任何域名
                .allowedOriginPatterns("*")
                //允许任何方法
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                //允许任何头
                .allowedHeaders("*")
                //暴露头
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}
