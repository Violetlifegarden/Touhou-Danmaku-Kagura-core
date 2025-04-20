package com.huashanlunjian.amara.music_game_core.chart;

import com.huashanlunjian.amara.music_game_core.AbstractChart;
import com.huashanlunjian.amara.utils.ChartUtil;

import java.util.List;
import java.util.Map;
/**Malody谱面解析我不再维护。目前暂时不考虑移除。*/
@Deprecated
public class MalodyChart extends AbstractChart<Map<String, Object>> {


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
    /**这玩意绝对有问题，之后得重写*/
    @Override
    public int getMaxTime() {
        return this.maxTime/10000;
    }
    public List<Map<String, Object>> getNotes() {
        return this.notes;
    }

    @Override
    public float getNoteTime(Integer index, float bpm) {
        return ChartUtil.getMalodyNoteTime(notes.get(index),this.bpm);
    }
}
