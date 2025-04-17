package com.huashanlunjian.amara.utils;

import com.huashanlunjian.amara.entity.songs.Boss;
import com.huashanlunjian.amara.music_game_core.SongsSummary;
import com.huashanlunjian.amara.network.message.ChangeDimensionPacket;
import com.huashanlunjian.amara.utils.sounds.OggPlayer;
import com.huashanlunjian.amara.worldgen.ModDimension;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MiscUtils {
    public static void enterMusicGame(SongsSummary summary, Player player) {
        //System.gc();
        //加载谱面
        //初始化音频播放器

        //预生成Boss
        PacketDistributor.sendToServer(new ChangeDimensionPacket(summary.getAudiofileInString(), summary.getChartPath()));
        //while (true){
            //if (player.level().dimension().equals(ModDimension.AMARADIM_LEVEL_KEY)){
//        CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(5000);
//            } catch (InterruptedException e) {
//                //throw new RuntimeException(e);
//            }
//            //Boss boss = new Boss(player,summary.getChart(),summary);
////            player.level().addFreshEntity(boss);
////                    //this.discard();
////                    return null;
//                }
//        );

                //break;
            //}
        //}

        //boss 生成

    }
    public static void delete(){
        //
    }
    public static void edit(){
        //
    }
}
