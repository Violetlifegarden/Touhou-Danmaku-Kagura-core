package com.huashanlunjian.amara.music_game_core;

import com.huashanlunjian.amara.api.IChartSet;

import java.util.List;

public abstract class AbstractChart<C> implements IChartSet {
    protected String title;
    protected String artist;
    protected String charter;
    protected float bpm = 0;
    protected int maxTime;
    protected List<C> notes;
    public AbstractChart(){

    }
    public float getBpm() {
        return bpm;
    }
    public String getDifficulty() {
        return "";
    }

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
    public List<C> getNotes() {
        return notes;
    }
    public abstract float getNoteTime(Integer index,float bpm);
}
