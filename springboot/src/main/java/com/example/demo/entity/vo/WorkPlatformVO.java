package com.example.demo.entity.vo;

import com.example.demo.entity.MonitorWork;
import com.example.demo.entity.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作品和平台的视图类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlatformVO {
    /**
     * 作品信息实体
     */
    private MonitorWork work;

    /**
     * 平台信息实体
     */
    private Platform platform;
}
