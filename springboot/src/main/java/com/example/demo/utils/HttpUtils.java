package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 发送http请求的工具类
 */
@Component
public class HttpUtils {

    @Autowired
    private RestTemplate restTemplate;

    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {

        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        httpHeaders.setContentType(type);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);

        return httpEntity;
    }


    public String generateRequestParameters(String protocol, String uri, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(protocol).append("://").append(uri);
        sb.append("?");
        for (Map.Entry map : params.entrySet()) {
            sb.append(map.getKey())
                    .append("=")
                    .append(map.getValue())
                    .append("&");
        }
        uri = sb.substring(0, sb.length() - 1);
        return uri;
    }

    /**
     * get请求、请求参数为?拼接形式的
     * <p>
     * 最终请求的URI如下：
     * <p>
     * http://127.0.0.1:80/?name=张三&age=20
     *
     * @return
     */
    public String get(String url, Map<String,String> map) {

        ResponseEntity responseEntity = restTemplate.getForEntity
                (
                        generateRequestParameters("http", url, map),
                        String.class
                );
        return (String) responseEntity.getBody();
    }

    public String post(String url, Map<String,String> jsonMap) {
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        url,
                        jsonMap,
                        String.class
                );
        return apiResponse.getBody();
    }

}
