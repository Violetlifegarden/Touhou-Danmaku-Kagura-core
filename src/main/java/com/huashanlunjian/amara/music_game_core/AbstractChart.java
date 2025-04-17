package com.huashanlunjian.amara.music_game_core;

import com.huashanlunjian.amara.api.IChartSet;

import java.util.Map;
import java.util.Queue;

public abstract class AbstractChart implements IChartSet {
    protected String title;
    protected String artist;
    protected String charter;
    protected float bpm = 0;
    protected int maxTime;
    protected Queue<Map<String, Object>> notes;
    public AbstractChart(Map<String, Object> chart){

    }
    public float getBpm() {
        return bpm;
    }
    public String getDifficulty() {
        return "";
    }
//    public  PriorityQueue<AbstractNote> getNotes() {
//        return notes;
//    }
    public String getArtist() {

        return "";
    }
    public String getTitle() {

        return "";
    }
    public String getCharter() {

        return "";
    }
    public int getMaxTime() {

        return 1000;
    }
    public Queue<Map<String, Object>> getNotes() {
        return notes;
    }

}
