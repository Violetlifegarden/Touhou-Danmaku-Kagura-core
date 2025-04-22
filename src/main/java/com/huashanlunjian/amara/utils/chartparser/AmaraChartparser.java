package com.huashanlunjian.amara.utils.chartparser;

import com.huashanlunjian.amara.utils.ChartUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class AmaraChartparser {
    //private final String filePath;
    private String title;
    private String artist;
    private String creator;
    //private double bpm;
    private int maxTime;
    private final Map<String, Object> originChart;
    private List<Map<String, Object>> notes;
    private List<Map<String, Object>> clientEvents;
    private List<Map<String, Object>> bossEvents;

    public AmaraChartparser(Path filePath) {
        this.originChart = ChartUtil.loadJson(filePath);
        this.title = originChart.get("title").toString();
        this.artist = originChart.get("artist").toString();
        this.creator = originChart.get("creator").toString();
    }
    public void parse() {
        this.maxTime = (int)originChart.get("max_time");
        this.notes = (List<Map<String, Object>>) originChart.get("note");

        if (originChart.get("client_events")!=List.of()) {
            this.clientEvents = (List<Map<String, Object>>) originChart.get("client_events");
        }
        if (originChart.get("boss_events")!=List.of()) {
            this.bossEvents = (List<Map<String, Object>>) originChart.get("boss_events");
        }
    }
    public String getTitle(){
        return this.title;
    }
    public String getArtist(){
        return this.artist;
    }
    public String getCreator(){
        return this.creator;
    }
    public int getMaxTime(){
        return this.maxTime;
    }
    public List<Map<String, Object>> getNotes(){
        return this.notes;
    }
    public List<Map<String, Object>> getClientEvents(){
        return this.clientEvents;
    }
    public List<Map<String, Object>> getBossEvents(){
        return this.bossEvents;
    }
}
