package com.huashanlunjian.amara.utils.chartparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OsuChartParser {
    private String filePath;
    private String title;
    private String artist;
    private String creator;
    //private double bpm;
    private int maxTime;
    private List<Integer> notes;

    public OsuChartParser(String filePath) {
        this.filePath = filePath;
    }
    public void parse() {
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
                        parseMetadata(reader, line);
                        break;

                    case "[HitObjects]":
                        notes = parseHitObject(reader); // 核心：解析击打物件
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**所以说，一定要确保等parseHitObject和parseMetadata完成后再调用其他内置变量*/
    private List<Integer> parseHitObject(BufferedReader reader) throws IOException {
        String line;
        List<Integer> list = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int time = Integer.parseInt(parts[2]);
            list.add(time);
        }
        this.maxTime = list.getLast();
        return list;
    }
    private void parseMetadata(BufferedReader reader,String line) throws IOException {
        while (!Objects.equals(line, "")) {
            String[] tmp = line.split(":");
            switch (tmp[0]) {
                case "Title":
                    this.title = tmp[tmp.length-1];
                    break;
                case "Artist":
                    this.artist = tmp[tmp.length-1];
                    break;
                case "Creator":
                    this.creator = tmp[tmp.length-1];
                    break;
//                case "AudioFilename":
//                    break;
//                case "AudioLeadIn":
//                    break;
//                case "PreviewTime":
//                    break;
//                case "Countdown":
//                    break;
            }
            line =reader.readLine();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCreator() {
        return creator;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public List<Integer> getNotes() {
        return notes;
    }
}



