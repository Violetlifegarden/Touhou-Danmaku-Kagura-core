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

    List<Map<String, Object>> getNoteEvents(int index);

    List<Map<String, Object>> getBossEvents();

    List<Map<String, Object>> getClientEvents();



}
