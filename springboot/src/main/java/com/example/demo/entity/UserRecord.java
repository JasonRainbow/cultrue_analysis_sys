package com.example.demo.entity;


import java.util.Date;

public class UserRecord {
    private Integer id;

    private Integer userId;

    private Integer workId;

    private Date accessTime;

    public UserRecord() {
    }

    public UserRecord(Integer id, Integer userId, Integer workId, Date accessTime) {
        this.id = id;
        this.userId = userId;
        this.workId = workId;
        this.accessTime = accessTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    @Override
    public String toString() {
        return "UserRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", workId=" + workId +
                ", accessTime=" + accessTime +
                '}';
    }
}
