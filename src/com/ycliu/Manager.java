package com.ycliu;

import com.ycliu.beans.Conference;
import com.ycliu.beans.Talk;
import com.ycliu.interfaces.IManager;
import com.ycliu.io.InputLoader;
import com.ycliu.io.OutputWriter;
import com.ycliu.manager.GreedyAlgorithm;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 程序入口及调度类
 * @author liuyichao
 */
public class Manager {

    private String inputFileName;
    private IManager manager;
    private InputLoader dataLoader;
    private OutputWriter outputWriter;

    /**
     * 构造方法
     * @param manager 安排算法
     */
    public Manager(IManager manager) {
        this.manager = manager;
        this.dataLoader = new InputLoader();
        this.outputWriter = new OutputWriter();
    }

    /**
     * 加载数据
     * @param fileName 文件名
     * @return talk列表
     */
    public List<Talk> loadTxt(String fileName) {
        this.inputFileName = fileName;
        List<Talk> data = new ArrayList<Talk>();
        try {
            dataLoader.loadTxt(fileName, data);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("文件未找到, 计算失败");
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Java IO 操作异常, 计算失败");
        }
        return data;
    }

    private static int calcTraceCount(List<Talk> data) {
        int sumTime = 0;
        for (Talk talking : data) {
            sumTime = sumTime + talking.getDuration();
        }
        int tracecount = sumTime / (60 * 7);
        int remainder = sumTime % (60 * 7);
        return remainder == 0 ? tracecount : tracecount + 1;
    }

    /**
     * 安排会议
     * @param data talk列表
     * @return 会议
     */
    public Conference arrange(List<Talk> data) {
        /**
         * 计算会议的Trace个数
         */
        int traceCount = calcTraceCount(data);
        Conference conference = new Conference(traceCount);
        manager.arrange(data, conference);
        return conference;
    }

    /**
     * 输出结果
     * @param conference 会议
     */
    public void exportResult(Conference conference) {
        try {
            String resultName = (inputFileName == null || "input.txt".equals(inputFileName))
                ? "output.txt" : inputFileName.replace(".txt", "-output.txt");
            outputWriter.outputResult(resultName, conference);
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Java IO 操作异常, 计算失败");
        }
    }

    public static void main(String[] arg) {
    	String fileName = "input.txt";
    	if (arg.length > 0) {
    		fileName = arg[0];
    	}
		Manager manager = new Manager(new GreedyAlgorithm());
		List<Talk> data = manager.loadTxt(fileName);
		if (data == null || data.isEmpty()) {
			System.out.println("获取数据失败, 计算失败");
			return;
		}
        Conference conference = manager.arrange(data);
        manager.exportResult(conference);
    }
}
