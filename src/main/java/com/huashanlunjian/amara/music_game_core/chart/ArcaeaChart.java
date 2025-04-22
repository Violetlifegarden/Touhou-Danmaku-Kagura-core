package com.huashanlunjian.amara.music_game_core.chart;

import com.huashanlunjian.amara.music_game_core.AbstractChart;

import java.util.List;
import java.util.Map;
@Deprecated
public class ArcaeaChart extends AbstractChart {
    public ArcaeaChart(Map<String, Object> chart) {
        super();
    }

    @Override
    public float getNoteTime(Integer index, float bpm) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> getNoteEvents(int index) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getBossEvents() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getClientEvents() {
        return null;
    }
}
