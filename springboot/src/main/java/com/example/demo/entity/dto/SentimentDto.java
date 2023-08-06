package com.example.demo.entity.dto;

import lombok.Data;

@Data
public class SentimentDto {
    private Integer happy = 0; // 开心评论数
    private Integer amazed = 0; // 惊讶评论数
    private Integer neutrality = 0; // 中立评论数
    private Integer sad = 0; // 伤心评论数
    private Integer angry = 0; // 愤怒评论数
    private Integer fear = 0; // 恐惧评论数

    public void happyAdd(Integer offset) {
        this.happy += offset;
    }

    public void amazedAdd(Integer offset) {
        this.amazed += offset;
    }

    public void neutralityAdd(Integer offset) {
        this.neutrality += offset;
    }

    public void sadAdd(Integer offset) {
        this.sad += offset;
    }

    public void angryAdd(Integer offset) {
        this.angry += offset;
    }

    public void fearAdd(Integer offset) {
        this.fear += offset;
    }
}
