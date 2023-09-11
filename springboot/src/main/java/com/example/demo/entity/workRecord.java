package com.example.demo.entity;

import java.util.Date;

public class workRecord {
    private String name;

    private String category;

    private Date accessTime;

    public workRecord() {
    }

    public workRecord(String name, String category, Date accessTime) {
        this.name = name;
        this.category = category;
        this.accessTime = accessTime;
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

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    @Override
    public String toString() {
        return "workRecord{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", accessTIme=" + accessTime +
                '}';
    }
}
