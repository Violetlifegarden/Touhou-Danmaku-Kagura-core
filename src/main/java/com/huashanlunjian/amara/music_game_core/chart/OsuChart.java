package com.huashanlunjian.amara.music_game_core.chart;

import com.huashanlunjian.amara.music_game_core.AbstractChart;
import com.huashanlunjian.amara.utils.chartparser.OsuChartParser;

import java.util.List;
import java.util.Map;

public class OsuChart extends AbstractChart<Integer> {
    public OsuChart(OsuChartParser parser) {
        super();
        parser.parse();
        this.title = parser.getTitle();
        this.artist = parser.getArtist();
        this.charter = parser.getCreator();
        this.maxTime = parser.getMaxTime();
        this.notes = parser.getNotes();
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
        return this.maxTime;
    }

    @Override
    public List<Map<String, Object>> getNoteMoveEvents() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getBossMoveEvents() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getBossRenderEvents() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getNoteRenderEvents() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getClientRenderEvents() {
        return null;
    }

    public List<Integer> getNotes() {
        return this.notes;
    }

    @Override
    public float getNoteTime(Integer index, float bpm) {
        return notes.get(index);
    }


}
