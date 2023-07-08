package com.example.demo.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Data
@Slf4j
@Builder
public class WordFreqDto {
    private String workName; // 作品名

    private String country; // 国家

    private String platform; // 平台

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time; // 时间

    private List<WordInfo> keywords;

    public static class WordInfo {
        private String word;
        private String polarity; // 情感极性
        private Integer counts; // 词频

        public WordInfo() {

        }

        public WordInfo(String word, String polarity, Integer counts) {
            this.word = word;
            this.polarity = polarity;
            this.counts = counts;
        }

        public void countsAdd(Integer num) {
            this.counts += num;
        }

        public String getPolarity() {
            return polarity;
        }

        public Integer getCounts() {
            return counts;
        }

        public String getWord() {
            return word;
        }
    }

//    private String wordCloudImg; // 词云图
}
