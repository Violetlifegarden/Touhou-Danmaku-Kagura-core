package com.huashanlunjian.amara.music_game_core;

import com.huashanlunjian.amara.entity.AbstractSongsEntity;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.entity.player.Player;

import java.util.Timer;
import java.util.TimerTask;

@Deprecated
public class GameThread implements Runnable{
    public final int[] time = {0};
    private final ServerBossEvent songProgress;
    private final Player player;
    private final AbstractSongsEntity entity;
    public GameThread( Player player, AbstractSongsEntity song) {
        this.player = player;
        this.entity = song;
        this.songProgress = entity.getSongProgress();
        //addKeyListener(this);
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        System.gc();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                time[0]++;
                if (!entity.level().isClientSide()) {
                    songProgress.setProgress((float) time[0] /entity.getMaxTime());
                }
                if (time[0] > entity.getMaxTime()) {
                    cancel();
                    entity.discard();
                }

            }
            public int getTime(){
                return time[0];
            }

        };
        timer.schedule(task, 1000, 1);
    }
}
