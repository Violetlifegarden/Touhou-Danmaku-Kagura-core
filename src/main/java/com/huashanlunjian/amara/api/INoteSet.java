package com.huashanlunjian.amara.api;

import com.huashanlunjian.amara.music_game_extension.events.NoteRenderEvents;

import java.util.List;
import java.util.Map;

public interface INoteSet {

    /**
     * note在什么时间加载*/
    int getTime();


    List<NoteRenderEvents> parseRenderEvents(List<Map<String, Object>> events);

    void parseNoteMoveEvent(List<Map<String, Object>> events);
}
