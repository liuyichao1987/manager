package com.ycliu.io;

import com.ycliu.beans.Talk;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Input数据加载类
 * @author liuyichao 
 */
public class InputLoader {

    private static Pattern talkPattern = Pattern.compile("(\\d+min)|(lightning)");

    public InputLoader() {
    }

    /**
     * 解析行
     * @param line 行数据
     * @throws UnsupportedEncodingException   
     */
    private Talk parseLine(String line) throws UnsupportedEncodingException {
        line = line.trim();
        if (line == null || line.isEmpty() || line.startsWith(";") || line.startsWith("#")) {
            return null;
        }
        Matcher matcher = talkPattern.matcher(line);
        if (matcher.find()) {
            String duration = matcher.group();
            String title = line.replace(duration, "").trim();
            if (duration.equals("lightning")) {
                return new Talk(title, 5);
            } else {
                try {
                    int mincount = Integer.decode(duration.replace("min", ""));
                    return new Talk(title, mincount);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * 加载Input数据文件
     * @param fileName Input数据文件
     * @param data talk列表
     * @throws FileNotFoundException FileNotFoundException异常
     * @throws IOException  IOException异常
     */
    public void loadTxt(String fileName, List<Talk> data) throws FileNotFoundException, IOException {
        loadTxt(new FileReader(fileName), data);
    }

    /**
     * 加载Input数据文件
     * @param input Input数据文件流
     * @param data talk列表
     * @throws FileNotFoundException FileNotFoundException异常
     * @throws IOException IOException异常
     */
    public void loadTxt(InputStreamReader input, List<Talk> data) throws FileNotFoundException, IOException {
        data.clear();
        BufferedReader reader = new BufferedReader(input);
        String line;
        while ((line = reader.readLine()) != null) {
            Talk talking = parseLine(line);
            if (talking != null) {
                data.add(talking);
            }
        }
        reader.close();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setId(i + 1);
        }
    }
}
