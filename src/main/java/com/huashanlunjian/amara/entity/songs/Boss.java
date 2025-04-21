package com.huashanlunjian.amara.entity.songs;

import com.huashanlunjian.amara.entity.AbstractNote;
import com.huashanlunjian.amara.entity.AbstractSongsEntity;
import com.huashanlunjian.amara.entity.Tap;
import com.huashanlunjian.amara.init.InitEntities;
import com.huashanlunjian.amara.network.message.BackToOverworldPacket;
import com.huashanlunjian.amara.utils.ChartUtil;
import com.huashanlunjian.amara.utils.sounds.MpegPlayer;
import com.huashanlunjian.amara.utils.sounds.OggPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.huashanlunjian.amara.utils.FileUtil.containsSimpleFileWithExtension;

public class Boss extends AbstractSongsEntity {
    private final AbstractSongsEntity entity;
    private Path audiofile ;
    private Random random = new Random();
    private List chartNotes;
    private int index = 0;
    /**如果把它单独放到每一个AbstractChart里面加载就很容易写了,
     * 但获取时可能会有问题，
     * 是否有必要重写queue？
     * 事实上使用通用的构建方式也并不是很难写*/
    private final Queue<AbstractNote> queue = new ConcurrentLinkedQueue<>();
    private Vec3 originSpeed = new Vec3(0,0,0);
    private int noteAmount;
    private int hit = 0;


    public Boss(EntityType<? extends AbstractSongsEntity> type, Level world) {
        super(type, world);
        this.entity = this;
    }
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
        this.noteAmount = chartNotes.size();
        for (int i = 0; i < chartNotes.size(); i++){
            queue.add(new Tap(this.level(), this.position().x, this.position().y, this.position().z,new Vec3(random.nextFloat()/4,-random.nextFloat()/4,random.nextFloat()/4), this));
        }
        //this.originspeed = chart.getOriginSpeed();

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public void tick(){
        super.tick();

        if (!this.level().isClientSide()) {
            //this.setDeltaMovement(this.originSpeed.add(player.getDeltaMovement()));
            this.setPos(player.getX()-9, player.getY()+7, player.getZ()-9);

            while (true){
                try {
                    if (chart.getNoteTime(index,bpm)<System.currentTimeMillis()-time[0]){
                        AbstractNote note = Objects.requireNonNull(queue.poll());
                        note.setPos(this.getX(), this.getY(), this.getZ());
                        this.level().addFreshEntity(note);
                        if (index<noteAmount-1){
                            index++;
                        }
                        else {
                            this.discard();
                            PacketDistributor.sendToPlayer((ServerPlayer) player,new BackToOverworldPacket(this.getHit(), this.getNoteAmount()));
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
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (!this.level().isClientSide()) {
            CompletableFuture.supplyAsync(() -> {
                System.gc();
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if (entity!=null) {
                            songProgress.setProgress((float) (System.currentTimeMillis()-time[0]) /entity.getMaxTime());
                        }

                    }

                };
                try {
                    if (containsSimpleFileWithExtension(audiofile, ".ogg")) {
                        OggPlayer.play(audiofile.toString());
                    }else MpegPlayer.play(audiofile.toString());
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
    public synchronized void onHit(){
        this.hit++;
    }
    public int getHit(){
        return this.hit;
    }
    public int getNoteAmount(){
        return this.noteAmount;
    }
}
