package com.huashanlunjian.amara.music_game_core.chart;

import com.huashanlunjian.amara.music_game_core.AbstractChart;
import com.huashanlunjian.amara.utils.ChartUtil;

import java.util.Map;

public class MalodyChart extends AbstractChart<Map<String, Object>> {
    //private Map<String, Object> chart;

    public MalodyChart(Map<String, Object> chart) {
        super();
        this.title = ChartUtil.getMalodyTitle(chart);
        this.artist = ChartUtil.getMalodyArtist(chart);
        this.charter = ChartUtil.getMalodyCharter(chart);
        this.bpm = ChartUtil.getMalodySimpleBPM(chart);
        this.maxTime = ChartUtil.getMalodyMaxTime(chart);
        this.notes = ChartUtil.getNotes(chart);
    }
    public String getDifficulty() {
        return "";
    }

    public String getArtist() {
        return this.artist;
    }
    public String getTitle() {
        return this.title;
    }
    public String getCharter() {
        return this.charter;
    }
    public float getBpm() {
        return this.bpm;
    }
    @Override
    public int getMaxTime() {
        return this.maxTime/10000;
    }
}
