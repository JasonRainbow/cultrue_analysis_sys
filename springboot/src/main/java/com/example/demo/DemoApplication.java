package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@PropertySource(value = "classpath:application.yml", encoding = "UTF-8")
@PropertySource(value = "classpath:application-prod.properties", encoding = "UTF-8")
@EnableWebSecurity   //开启security
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
