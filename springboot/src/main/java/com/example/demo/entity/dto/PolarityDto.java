package com.example.demo.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolarityDto {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date postTime; // 时间

    private Integer positive = 0; // 积极评论数
    private Integer negative = 0; // 消极评论数
    private Integer neutrality = 0; // 中立评论数

    public void positiveAdd(Integer offset) {
        this.positive += offset;
    }

    public void negativeAdd(Integer offset) {
        this.negative += offset;
    }

    public void neutralityAdd(Integer offset) {
        this.neutrality += offset;
    }
}
