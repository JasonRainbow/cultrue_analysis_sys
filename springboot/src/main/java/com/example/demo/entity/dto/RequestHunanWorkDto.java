package com.example.demo.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author hzx
 * 查询湖南的文化作品时前端需要传递的数据
 */
@Data
public class RequestHunanWorkDto {
    /**
     * 页大小
     */
    private Integer pageNum = 1;

    /**
     * 页号
     */
    private Integer pageSize = 10;

    /**
     * 作品子类型
     */
    private List<String> subCategories;

    /**
     * 作品所属的地域
     */
    private List<String> origins;
}
