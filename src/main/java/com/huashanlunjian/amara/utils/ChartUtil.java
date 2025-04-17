package com.huashanlunjian.amara.utils;

import com.fasterxml.jackson.jr.ob.JSON;
import com.huashanlunjian.amara.music_game_core.AbstractChart;
import com.huashanlunjian.amara.music_game_core.chart.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChartUtil {
    public static AbstractChart loadChart(Path path){
        return switch (ChartCategories.getChartCategory(path).getCategory()){
            case ".mc" -> new MalodyChart(loadJson(path));
            case ".aff" -> new ArcaeaChart(loadJson(path));
            case ".osu" -> new OsuChart(loadJson(path));
            case ".json" -> new PhiraChart(loadJson(path));
            case "amara" -> new DefaultChart(loadJson(path));
            default -> throw new RuntimeException("谱面格式错误");
        };
    }
    public static Map<String, Object> loadJson(Path path){
        try {
            String jsonContent = new String(Files.readAllBytes(path));
            return JSON.std.mapFrom(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*Malody谱面形式
    "meta": {
        "$ver": 0,
        "creator": "ZxdzWa901",
        "background": "tmp_1587972078.jpg",
        "version": "",
        "id": 0,
        "mode": 0,
        "time": 1588004231,
        "song": {
            "title": "Dancing line-The desert",
            "artist": "Unknown",
            "id": 0,
            "titleorg": "Dancing Line-The desert"
        },
        "mode_ext": {
            "column": 4,
            "bar_begin": 0
        }
    }, */
    /**
     * 仅适用于Malody谱面*/
    private static Map<String, Object> getMalodyMeta(Map<String, Object> json){
        return (Map<String, Object>) json.get("meta");
    }
    public static String getMalodyCharter(Map<String, Object> json){
        return (String) getMalodyMeta(json).get("creator");
    }
    /**
     * 仅适用于Malody谱面*/
    private static Map<String, Object> getMalodySong(Map<String, Object> json){
        return (Map<String, Object>) getMalodyMeta(json).get("song");
    }
    public static  int getMalodyMaxTime(Map<String, Object> json){
        return (int) getMalodyMeta(json).get("time");
    }
    public static String getMalodyArtist(Map<String, Object> json){
        return (String) getMalodySong(json).get("artist");
    }
    public static String getMalodyTitle(Map<String, Object> json){
        return getMalodySong(json).get("titleorg")==null?(String) getMalodySong(json).get("title"):getMalodyTitleOrg(json);
    }
    private static String getMalodyTitleOrg(Map<String, Object> json){
        return (String) getMalodySong(json).get("titleorg");
    }

    /*  Malody 谱面形式
    "time": [
        {
            "beat": [
                0,
                0,
                1
            ],
            "bpm": 124.0
        }
    ],
    "effect": [],
    "note": [
            {
                "beat": [
                    3,
                    0,
                    4
                ],
                "column": 2
            },
            {
                "beat": [
                    3,
                    0,
                    4
                ],
                "column": 3
            },
    * */
    /**适用于bpm不变的谱面*/
    public static float getMalodySimpleBPM(Map<String, Object> json){
        Object bpmValue= ((List<Map<String, Object>>) json.get("time")).get(0).get("bpm");
        return ((Number) bpmValue).intValue();
    }
    /**这个暂时不要用，变速逻辑还没有实现，事实上我目前还不了解malody谱面的变速格式什么样*/
    public static List<Map<String, Object>> getMalodyBPM(Map<String, Object> json){
        return (List<Map<String, Object>>) json.get("time");
    }
    /**这个是Malody和Amara格式专用的*/
    public static List<Map<String, Object>> getNotes(Map<String, Object> json){
        return (List<Map<String, Object>>) json.get("note");
    }
    /**这个是Malody专用的,但在解析时需要指明谱面类型。*/
//    public static PriorityQueue<Map<String, Object>> getMalodyNotesPriorityQueue(List<Map<String, Object>> notes){
//        PriorityQueue<Map<String, Object>> notesPriorityQueue = new PriorityQueue<>((o1, o2) -> {
//            int o1Time = ((List<Integer>) o1.get("beat")).getFirst();
//            int o2Time = ((List<Integer>) o1.get("beat")).getFirst();
//            return o1Time - o2Time;
//        });
//        notesPriorityQueue.addAll(notes);
//        return notesPriorityQueue;
//    }
    public static Queue<Map<String, Object>> getMalodyNotesPriorityQueue(List<Map<String, Object>> notes){

        return new ConcurrentLinkedQueue<>(notes);
    }
    public static float getMalodyNoteTime(Map<String, Object> note,float bpm){
        List<Integer> beat = (List<Integer>) note.get("beat");
        return (((beat).getFirst()+beat.get(1)/beat.get(2))* 60000)/bpm;
    }

}
