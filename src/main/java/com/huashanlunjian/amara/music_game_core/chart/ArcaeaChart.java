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
}
