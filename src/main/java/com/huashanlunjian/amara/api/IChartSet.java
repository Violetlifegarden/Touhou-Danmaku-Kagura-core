package com.huashanlunjian.amara.api;

import java.util.List;
import java.util.Map;

public interface IChartSet {

    String getTitle();

    String getArtist();

    String getDifficulty();

    String getCharter();

    float getBpm();

    int getMaxTime();

    List<Map<String, Object>> getNoteMoveEvents();

    List<Map<String, Object>> getBossMoveEvents();

    List<Map<String, Object>> getBossRenderEvents();

    List<Map<String, Object>> getNoteRenderEvents();

    List<Map<String, Object>> getClientRenderEvents();



}
