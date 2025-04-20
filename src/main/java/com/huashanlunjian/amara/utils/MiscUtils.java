package com.huashanlunjian.amara.utils;

import com.huashanlunjian.amara.music_game_core.SongsSummary;
import com.huashanlunjian.amara.network.message.ChangeDimensionPacket;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class MiscUtils {
    public static void enterMusicGame(SongsSummary summary, Player player) {
        PacketDistributor.sendToServer(new ChangeDimensionPacket(summary.getAudiofileInString(), summary.getChartPath()));
    }
    public static void delete(){
        //
    }
    public static void edit(){
        //
    }
}
