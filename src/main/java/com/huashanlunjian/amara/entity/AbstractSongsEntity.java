package com.huashanlunjian.amara.entity;

import com.huashanlunjian.amara.api.ISongSet;
import com.huashanlunjian.amara.music_game_core.AbstractChart;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Map;


public abstract class AbstractSongsEntity extends Entity implements ISongSet {
    public final long[] time = {0};
    protected ServerBossEvent songProgress = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS);
    protected Player player;
    @Deprecated
    protected AbstractChart chart;
    protected int songTime;
    protected Map<String, Object> temp = null;
    protected float bpm;

    public AbstractSongsEntity(EntityType<? extends AbstractSongsEntity> type, Level world) {
        super(type, world);}

    public int getMaxTime(){
        return this.songTime;
    }
    public ServerBossEvent getSongProgress(){
        return songProgress;
    }

}
