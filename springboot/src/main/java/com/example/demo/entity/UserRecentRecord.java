package com.example.demo.entity;

import java.util.Date;

public class UserRecentRecord {
    private Integer id;
    private Integer userId;
    private Integer workId;
    private Integer visitCounts;
    private Date recentVisit;

    public UserRecentRecord() {
    }

    public UserRecentRecord(Integer id, Integer userId, Integer workId, Integer visitCounts, Date recentVisit) {
        this.id = id;
        this.userId = userId;
        this.workId = workId;
        this.visitCounts = visitCounts;
        this.recentVisit = recentVisit;
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

    public Integer getVisitCounts() {
        return visitCounts;
    }

    public void setVisitCounts(Integer visitCounts) {
        this.visitCounts = visitCounts;
    }

    public Date getRecentVisit() {
        return recentVisit;
    }

    public void setRecentVisit(Date recentVisit) {
        this.recentVisit = recentVisit;
    }

    @Override
    public String toString() {
        return "UserRecentRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", workId=" + workId +
                ", visitCounts=" + visitCounts +
                ", recentVisit=" + recentVisit +
                '}';
    }
}
