/*
 * @(#)OuputWriter.java	2019-03-26
 *
 * Copyright 2010 Fiberhome. All rights reserved.
 */
package com.ycliu.io;

import com.ycliu.beans.Conference;
import com.ycliu.beans.Period;
import com.ycliu.beans.Talk;
import com.ycliu.beans.Trace;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 结果输出类
 * @author liuyichao
 * @since 1.0
 */
public class OutputWriter {

    public OutputWriter() {
    }

    /**
     * 输出结果
     * @param resultName 文件名
     * @param conference 输出数据
     * @throws IOException IOException异常
     */
    public void outputResult(String resultName, Conference conference) throws IOException {
        BufferedWriter xsdWriter = new BufferedWriter(new FileWriter(resultName));
        List<Trace> tracelist = conference.getTraceList();
        for (int i = 0; i < tracelist.size();i++) {
        	Trace trace = tracelist.get(i);
			if (i % 2 == 0) {
				if (i != 0) {
					xsdWriter.append("\r\n");
				}
				xsdWriter.append(trace.getTraceTitle() + "\r\n");
			}
            String periodString = trace.getPeriod().toString();
            int hour = trace.getPeriod() == Period.AM ? 9 : 1;
            int sumTime = 0;
            for (Talk talk : trace.getTalklist()) {
                int startHour = hour + sumTime / 60;
                int startMin = sumTime % 60;
                String formatString = String.format("%02d:%02d%s %s",
                    startHour, startMin, periodString, talk.getTalkInfo());

                xsdWriter.append(formatString + "\r\n");
                sumTime = sumTime + talk.getDuration();
            }
			int endHour = hour + sumTime / 60;
			int endMin = sumTime % 60;
			if (endHour == 12) {
				periodString = "PM";
			}
			if (i % 2 == 0) {
				String formatString = String.format("%02d:%02d%s %s", endHour, endMin, periodString, "Lunch");
				xsdWriter.append(formatString + "\r\n");
//				xsdWriter.append("12:00PM Lunch" + "\r\n");
			} else {
				String formatString = String.format("%02d:%02d%s %s", endHour, endMin, periodString,
						"Networking Event");
				xsdWriter.append(formatString + "\r\n");
//				xsdWriter.append("05:00PM Networking Event" + "\r\n");
			}

        }
        xsdWriter.close();
    }
}
