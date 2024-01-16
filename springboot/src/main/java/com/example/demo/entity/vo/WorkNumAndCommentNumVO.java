package com.example.demo.entity.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class WorkNumAndCommentNumVO {
    private String keyName;

    private Integer workCnt;

    private Integer commentCnt;
}
