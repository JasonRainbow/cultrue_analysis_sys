package com.example.demo.entity;

import java.util.Date;

public class workRecentRecord {
    private String name;
    private String category;
    private Integer visitCounts;
    private Date recentVisit;

    public workRecentRecord() {
    }

    public workRecentRecord(String name, String category, Integer visitCounts, Date recentVisit) {
        this.name = name;
        this.category = category;
        this.visitCounts = visitCounts;
        this.recentVisit = recentVisit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return "workRecentRecord{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", visitCounts=" + visitCounts +
                ", recentVisit=" + recentVisit +
                '}';
    }
}
