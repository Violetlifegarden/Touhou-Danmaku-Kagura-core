package com.huashanlunjian.amara.music_game_core.chart;

import com.huashanlunjian.amara.music_game_core.AbstractChart;

import java.util.List;
import java.util.Map;

public class DefaultChart extends AbstractChart {
    public DefaultChart(Map<String, Object> chart) {
        super();
    }

    @Override
    public float getNoteTime(Integer index, float bpm) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> getNoteMoveEvents() {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getBossMoveEvents() {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getBossRenderEvents() {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getNoteRenderEvents() {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getClientRenderEvents() {
        return List.of();
    }
}
