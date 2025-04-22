package com.huashanlunjian.amara.music_game_extension.events;

import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Function;

public class NoteMoveEvents{
    private Function<double[], Vec3> onNoteMove;
    private double[] args;

    public NoteMoveEvents(Function<double[], Vec3> onNoteMove, List<Double> args) {
        this.onNoteMove = onNoteMove;
        this.args = args.stream().mapToDouble(Double::doubleValue).toArray();
    }
    public Vec3 onNoteMove() {
        return onNoteMove.apply(this.args);
    }


}
