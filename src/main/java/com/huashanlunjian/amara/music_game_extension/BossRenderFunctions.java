package com.huashanlunjian.amara.music_game_extension;

import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.function.Function;

/*
* {
"time": 1000,
"type": "render1",
"args": [1,2,3,...]
},*/
public class BossRenderFunctions {
    private static final Map<String, Function> bossRenderFunctions = Map.of(
            "example_render_function",(args)-> {
                return args;
            }
    );
    public static Function getFunction(String propertyName){
        return bossRenderFunctions.get(propertyName);
    }


}
