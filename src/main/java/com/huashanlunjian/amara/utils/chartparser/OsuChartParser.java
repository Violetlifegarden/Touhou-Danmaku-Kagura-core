package com.huashanlunjian.amara.utils.chartparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OsuChartParser {
    private String title;
    private String artist;
    private String difficulty;
    private double bpm;
    private int maxTime;
    private List<Map<String, Object>> notes;
    public void parse(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentSection = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[")) {
                    currentSection = line.trim(); // 识别当前区块 [General]/[Metadata] 等
                    continue;
                }

                switch(currentSection) {
                    case "[Metadata]":
                        //parseMetadata(line);
                        break;

                    case "[HitObjects]":
                        parseHitObject(line); // 核心：解析击打物件
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 解析单个击打物件 (HitObject)
    private int parseHitObject(String line) {
        String[] parts = line.split(",");
        //int x = Integer.parseInt(parts[0]);
        //int y = Integer.parseInt(parts[1]);
        int time = Integer.parseInt(parts[2]);
        //int type = Integer.parseInt(parts[3]);
        // 根据 type 进行不同物件的具体解析...
        return time;
    }
}



