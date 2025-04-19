package com.huashanlunjian.amara.music_game_core.chart;

import com.huashanlunjian.amara.music_game_core.AbstractChart;
import com.huashanlunjian.amara.utils.chartparser.OsuChartParser;

import java.util.List;
import java.util.Map;

public class OsuChart extends AbstractChart<Integer> {
    //private List<Integer> osunotes;
    public OsuChart(OsuChartParser parser) {
        super();
        this.title = parser.getTitle();
        this.artist = parser.getArtist();
        this.charter = parser.getCreator();
        this.maxTime = parser.getMaxTime();
        this.notes = parser.getNotes();


    }
    //@Override

}
