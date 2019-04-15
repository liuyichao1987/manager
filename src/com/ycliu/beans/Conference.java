package com.ycliu.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Conference定义
 * @author liuyichao
 */
public class Conference {
    
    private List<Trace> traceList;
    private int traceCount;

    /**
     * 构造
     * @param traceCount trace个数
     */
    public Conference(int traceCount) {
        this.traceCount = traceCount > 0 ? traceCount : 1;
        this.traceList = new ArrayList<Trace>();
        initTraces();
    }

    private void initTraces() {
        /**
         * 初始化traceList，每个traceid 生成AM、PM各一个Trace
         */
        for (int i = 0; i < traceCount; i++) {
            traceList.add(new Trace(Period.PM, i));
            traceList.add(new Trace(Period.AM, i));
        }
    }

    public List<Trace> getTraceList() {
        return traceList;
    }
}
