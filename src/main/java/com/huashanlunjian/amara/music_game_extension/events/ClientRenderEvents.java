package com.huashanlunjian.amara.music_game_extension.events;

import com.huashanlunjian.amara.api.IEventSet;
import com.huashanlunjian.amara.entity.songs.Boss;

import java.util.List;
import java.util.function.Function;

public class ClientRenderEvents<T> {
    private Function<Boss, Void> clientFunction;
    //private double[] args;
    private int starttime;
    private int endtime;

    public ClientRenderEvents(Function<Boss,Void> function, List<Double> args, int startTime, int endTime) {
        this.clientFunction = function;
        this.starttime = startTime;
        this.endtime = endTime;
    }
    public void renderClient(Boss boss){
        if(boss.getTime()>=starttime&&boss.getTime()<=endtime){
            this.clientFunction.apply(boss);
        }
    }

    public long getEndtime() {
        return endtime;
    }
}
