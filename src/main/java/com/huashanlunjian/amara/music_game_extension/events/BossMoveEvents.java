package com.huashanlunjian.amara.music_game_extension.events;

import com.huashanlunjian.amara.api.IEventSet;
import com.huashanlunjian.amara.entity.songs.Boss;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Function;

public class BossMoveEvents{
    private Function<double[], Vec3> onBossMove;
    private double[] args;
    private int starttime;
    private int endtime;

    public BossMoveEvents(Function<double[], Vec3> onNoteMove, List<Double> args,int starttime,int endtime) {
        this.onBossMove = onNoteMove;
        this.args = args.stream().mapToDouble(Double::doubleValue).toArray();
        this.starttime = starttime;
        this.endtime = endtime;
    }
    public Vec3 onNoteMove(Boss boss) {

        if (boss.getTime()>=starttime) {
            return onBossMove.apply(this.args);
        }
        return boss.getOriginSpeed();
    }

    public int getEndtime() {
        return endtime;
    }
}
