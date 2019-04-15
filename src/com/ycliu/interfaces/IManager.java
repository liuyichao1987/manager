/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ycliu.interfaces;

import com.ycliu.beans.Conference;
import com.ycliu.beans.Talk;
import java.util.List;

/**
 * 会议安排算法接口
 * @author liuyichao
 */
public interface IManager {
    
    /**
     * 安排会议
     * @param talkList talk列表
     * @param conference 会议
     */
    public void arrange(List<Talk> talkList, Conference conference) ;
    
}
