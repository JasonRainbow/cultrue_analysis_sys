package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("monitor_request")
@Data
@Builder
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
@ApiModel(value = "监测请求实体", description = "监测请求实体")
public class MonitorRequest {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id; // 自增主键

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户")
    private User user; // 用户 不是表的字段

    @ApiModelProperty(value = "监测作品ID")
    private Integer workId; // 监测文化作品id

    @TableField(exist = false)
    @ApiModelProperty(value = "监测作品")
    private MonitorWork monitorWork; // 监测作品， 不在表中

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "监测请求创建的时间")
    private Date createTime; // 监测请求创建的时间

    @ApiModelProperty(value = "是否通过审核")
    private Short checked = 0; // 请求是否通过审核

    @ApiModelProperty(value = "是否完成监测")
    private Short complete = 0; // 是否完成
}
