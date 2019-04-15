/*
 * @(#)Trace.java	2019-03-26
 *
 * Copyright 2010 Fiberhome. All rights reserved.
 */
package com.ycliu.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace定义
 * @author liuyichao
 */
public class Trace {
    
    private int traceId;
    private Period period;
    private int periodLength;
    
    private List<Talk> talklist;

    /**
     * 构造方法
     * @param period 时间段 AM-上午，PM下午
     * @param traceId trace编号
     */
    public Trace(Period period, int traceId) {
        this.period = period;
        this.traceId = traceId;
        this.talklist = new ArrayList<Talk>();
        this.periodLength = period == Period.AM ? 180 : 240;
    }
    
    /**
     * add Talk
     * @param talk talk
     */
    public void addTalk(Talk talk) {
        this.talklist.add(talk);
    }
    
    /**
     * remove Talk
     * @param talk talk
     */
    public void removeTalk(Talk talk) {
        this.talklist.add(talk);
    }

    public Period getPeriod() {
        return period;
    }

    public int getTraceId() {
        return traceId;
    }

    public List<Talk> getTalklist() {
        return talklist;
    }
    
    /**
     * 获取Trace中已安排Talk个数
     * @return Talk个数
     */
    public int getTalkCount() {
        return talklist != null ? talklist.size() : 0;
    }
    
    /**
     * 获取Trace中已安排Talk总时长
     * @return Trace中已安排Talk总时长
     */
    public int getSumTime() {
        if (talklist.isEmpty()) {
            return 0;
        }
        int usedTime = 0;
        for (Talk talk : talklist) {
            usedTime = usedTime + talk.duration;
        }
        return usedTime;
    }
    
    /**
     * 获取Trace剩余时长
     * @return Trace剩余时长
     */
    public int getRemainTime() {
        return periodLength - getSumTime();
    }
    
    /**
     * 获取Trace名称
     * @return Trace名称
     */
    public String getTraceName() {
        return "Trace " + (traceId + 1) + " " + period.toString();
    }
    
    /**
     * 获取Trace显示名称
     * @return Trace显示名称
     */
    public String getTraceTitle() {
        return "Trace " + (traceId + 1) + ":";
    }
}
