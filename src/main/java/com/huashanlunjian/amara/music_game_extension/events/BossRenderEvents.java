package com.huashanlunjian.amara.music_game_extension.events;

import com.huashanlunjian.amara.entity.songs.Boss;

import java.util.List;
import java.util.function.Function;
/**应该全部使用void就够*/
public class BossRenderEvents{//<T> {
    private final Function<Boss, Void> bossFunction;
    //private double[] args;
    private int starttime;
    private int endtime;

    public BossRenderEvents(Function<Boss,Void> function, List<Double> args, int startTime, int endTime) {
        this.starttime=startTime;
        this.endtime=endTime;
        this.bossFunction =function;
    }
    public void renderBoss(Boss boss){
        if(boss.getTime()>=starttime&&boss.getTime()<=endtime){
            this.bossFunction.apply(boss);
        }
    }

    public long getEndtime() {
        return endtime;
    }
}
