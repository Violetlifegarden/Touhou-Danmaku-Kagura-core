package com.huashanlunjian.amara.music_game_core.chart;

import com.huashanlunjian.amara.music_game_core.AbstractChart;
import com.huashanlunjian.amara.utils.chartparser.AmaraChartparser;

import java.util.List;
import java.util.Map;

public class AmaraChart extends AbstractChart {
    private List<Map<String, Object>> clientEvents;
    private List<Map<String, Object>> bossEvents;
    /*
    * This is the designed format for .amara chart.
    * {
    *     "title": "title",
    *     "artist": "artist",
    *     "charter": "charter",
    *     "max_time": 100000,
    *     "note": [
    *           {
    *               "type": "tap",
    *               "time": 0,
    *               "xspeed": 0,
    *               "yspeed": 0,
    *               "zspeed": 0,
    *               "events": [
    *                   {
    *                       "type": "render",
    *                       "functions": "",
    *                       "args": [1,2,3,...]
    *                   },
    *                   {
    *                       "type": "change_speed",
    *                       "functions": "example",
    *                       "args": [1,2,3]
    *                   }
    *               ]
    *           }
    *       ],
    *       "boss_events": [
    *           {
    *               "time": 1000,
    *               "type": "render1",
    *               "args": [1,2,3,...]
    *           },
    *           {
    *               "time": 2000,
    *               "type": "render2",
    *               "return_category":"void",
    *               "args": [1,2,3,...]
    *           },
    *           {
    *               "time": 3000,
    *               "type": "change_speed",
    *               "args": [1,2,3]
    *           }
    *       ],
    *       "client_events": [
    *           {
    *               "time": 1000,
    *               "type": "render",
    *               "args": [1,2,3,...]
    *           }
    *       ]
    * }*/
    public AmaraChart(AmaraChartparser parser) {
        super();
        parser.parse();
        this.title = parser.getTitle();
        this.artist = parser.getArtist();
        this.charter = parser.getCreator();
        this.maxTime = parser.getMaxTime();
        this.notes = parser.getNotes();
        this.clientEvents = parser.getClientEvents();
        this.bossEvents = parser.getBossEvents();
    }

    @Override
    public float getNoteTime(Integer index, float bpm) {
        return 0;
    }
    /**先根据index获取特定的note，然后再获取events，形式是List*/
    @Override
    public List<Map<String, Object>> getNoteEvents(int index) {
        return (List<Map<String, Object>>) ((Map<String, Object>)notes.get(index)).get("events");
    }

    @Override
    public List<Map<String, Object>> getBossEvents() {
        return this.bossEvents;
    }

    @Override
    public List<Map<String, Object>> getClientEvents() {
        return this.clientEvents;
    }
}
