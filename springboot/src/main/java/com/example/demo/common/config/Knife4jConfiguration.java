package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Knife4jConfiguration {

    // 地址 ip:port/doc
    @Bean(value = "SpringBoot-Vue-Demo-Api")
    public Docket cloudMallApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("接口文档列表")
                        .description("接口文档")
                        .termsOfServiceUrl("http://www.springboot.vue.com")
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    // 基本信息设置
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "米大傻", // 作者姓名
                "https://blog.csdn.net/xhmico?type=blog", // 作者网址
                "777777777@163.com"); // 作者邮箱
        return new ApiInfoBuilder()
                .title("多加辣-接口文档") // 标题
                .description("众里寻他千百度，慕然回首那人却在灯火阑珊处") // 描述
                .termsOfServiceUrl("https://www.baidu.com") // 跳转连接
                .version("1.0") // 版本
                .license("Swagger-的使用(详细教程)")
                .licenseUrl("https://blog.csdn.net/xhmico/article/details/125353535")
                .contact(contact)
                .build();
    }
}
