package com.huashanlunjian.amara.music_game_extension;

import com.huashanlunjian.amara.entity.AbstractNote;

/*"events": [
{
"type": "render",
"functions": "renderFire"
},*/
/**note的渲染事件就不要放到tick里面了，至多改一改note参数,也不要往里面传参了*/
public class NoteRenderFunctions {
    public static void renderFire(AbstractNote note) {
        note.wasOnFire = true;
    }
}
