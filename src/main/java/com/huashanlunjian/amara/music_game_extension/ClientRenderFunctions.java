package com.huashanlunjian.amara.music_game_extension;

import java.util.Map;
import java.util.function.Function;

public class ClientRenderFunctions {
    private static final Map<String, Function> clientRenderFunctions = Map.of(
            "example_render_function",(args)-> {
                return args;
            }
    );
    public static Function getFunction(String propertyName){
        return clientRenderFunctions.get(propertyName);
    }
}
