/*
 * @(#)Talking.java	2019-03-26
 *
 * Copyright 2010 Fiberhome. All rights reserved.
 */
package com.ycliu.beans;

/**
 * Talk定义
 * @author liuyichao
 */
public class Talk {
    int id;
    String title;
    int duration;

    /**
     * 构造方法
     * @param title 
     * @param duration 
     */
    public Talk(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
    
    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 获取Talking信息，包括标题及时长
     * @return Talking信息
     */
    public String getTalkInfo() {
        if (title == null || title.isEmpty()) {
            return null;
        }
        String durationString = duration == 5 ? "lightning" : duration + "min";
        return title + " " + durationString;
    }
}
