package com.example.demo.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "interceptorconfig.path")
public class ConfigProperties {
    private List<String> permitAll;

}
