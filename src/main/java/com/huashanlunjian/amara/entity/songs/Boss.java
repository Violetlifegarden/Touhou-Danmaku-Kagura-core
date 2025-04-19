package com.huashanlunjian.amara.entity.songs;

import com.huashanlunjian.amara.entity.AbstractSongsEntity;
import com.huashanlunjian.amara.entity.Tap;
import com.huashanlunjian.amara.init.InitEntities;
import com.huashanlunjian.amara.utils.ChartUtil;
import com.huashanlunjian.amara.utils.sounds.OggPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.sound.sampled.AudioSystem;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Boss extends AbstractSongsEntity {
    private final AbstractSongsEntity entity;
    private Path audiofile ;
    private Random random = new Random();
    private List chartNotes;
    private int index = 0;


    public Boss(EntityType<? extends AbstractSongsEntity> type, Level world) {
        super(type, world);
        this.entity = this;
    }
    /**SongsSummary决定要移除了,想点其他办法获取音频路径吧*/
    public Boss(Player player, String chartPath, Path audiofile) {
        super(InitEntities.DEMOSONG.get(), player.level());
        this.audiofile = audiofile;
        this.player = player;
        this.chart = ChartUtil.loadChart(Path.of(chartPath));
        this.chartNotes = chart.getNotes();
        this.songTime=(chart.getMaxTime());
        this.bpm = chart.getBpm();
        this.setCustomName(Component.literal(chart.getTitle()));
        this.setPos(new Vec3(player.getX(),player.getY()+10,player.getZ()));
        this.entity = this;

    }
    public void tick(){
        super.tick();

        if (!this.level().isClientSide()) {

            while (true){
                try {
                    if (chart.getNoteTime(index,bpm)<System.currentTimeMillis()-time[0]){
                        Tap tap = new Tap(this.level(), this.position().x, this.position().y, this.position().z,new Vec3(2*random.nextFloat()-1,-random.nextFloat(),2*random.nextFloat()-1), this);
                        this.level().addFreshEntity(tap);
                        if (index<chartNotes.size()-1){
                            index++;
                        }
                        else {
                            this.discard();
                            //PacketDistributor.sendToServer(new BackToOverworldPacket(true));
                            break;
                        }
                    }else break;
                } catch (NullPointerException e) {
                    this.discard();
                }
            }
        }
    }


    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.songProgress.setName(this.getDisplayName());
    }
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.songProgress.addPlayer(player);
        this.songProgress.setProgress(0);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.songProgress.removePlayer(player);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        if (!this.level().isClientSide()) {
            CompletableFuture.supplyAsync(() -> {
                System.gc();


                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if (entity!=null) {
                            songProgress.setProgress((float) (System.currentTimeMillis()-time[0]) /entity.getMaxTime());
//                            if (System.currentTimeMillis()-time[0] > entity.getMaxTime()) {
//                                entity.discard();
//                                cancel();
//                            }
                        }

                    }

                };
                try {
                    AudioSystem.getAudioFileTypes();
                    OggPlayer.play(audiofile.toString());
                } catch (Exception e) {
                    this.discard();
                }
                time[0]=System.currentTimeMillis();
                timer.schedule(task, 0, 1);
                return null;
                }
            );
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     *
     * @param compound
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }
}
