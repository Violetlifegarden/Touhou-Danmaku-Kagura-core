package com.huashanlunjian.amara.entity.songs;

import com.huashanlunjian.amara.entity.AbstractNote;
import com.huashanlunjian.amara.entity.AbstractSongsEntity;
import com.huashanlunjian.amara.entity.Tap;
import com.huashanlunjian.amara.init.InitEntities;
import com.huashanlunjian.amara.music_game_extension.BossMoveFunctions;
import com.huashanlunjian.amara.music_game_extension.BossRenderFunctions;
import com.huashanlunjian.amara.music_game_extension.BossType;
import com.huashanlunjian.amara.music_game_extension.events.BossMoveEvents;
import com.huashanlunjian.amara.music_game_extension.events.BossRenderEvents;
import com.huashanlunjian.amara.music_game_extension.events.ClientRenderEvents;
import com.huashanlunjian.amara.network.message.BackToOverworldPacket;
import com.huashanlunjian.amara.utils.ChartUtil;
import com.huashanlunjian.amara.utils.sounds.MpegPlayer;
import com.huashanlunjian.amara.utils.sounds.OggPlayer;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.huashanlunjian.amara.utils.FileUtil.containsSimpleFileWithExtension;

public class Boss extends AbstractSongsEntity implements FlyingAnimal {
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
    private Vec3 originPosition = new Vec3(-10,7,-10);
    private int noteAmount;
    private int hit = 0;
    ////////////////////////////////////////////////////
    private final Queue<BossRenderEvents> bossRenderEvents = new ConcurrentLinkedQueue<>();
    private final Queue<ClientRenderEvents> clientRenderEvents = new ConcurrentLinkedQueue<>();
    private final Queue<BossMoveEvents> bossMoveEvents = new ConcurrentLinkedQueue<>();
    private BossRenderEvents bossRenderEventTemp;
    private ClientRenderEvents clientRenderEventTemp;
    private BossMoveEvents bossMoveEventTemp;
    private boolean moveEmpty = false;
    private boolean bossrenderEmpty = false;
    private boolean clientrenderEmpty = false;
    /////////////////////////////////////////////////////
    private static final EntityDataAccessor<Integer> FAIRY_TYPE = SynchedEntityData.defineId(Boss.class, EntityDataSerializers.INT);




    public Boss(EntityType<? extends AbstractSongsEntity> type, Level world) {
        super(type, world);
        this.entity = this;
        this.moveControl = new FlyingMoveControl(this, 15, true);

    }
    public Boss(Player player, String chartPath, Path audiofile) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
        for (int i = 0; i < noteAmount; i++){
            queue.add(new Tap(this.level(), this.position().x, this.position().y, this.position().z,new Vec3(random.nextFloat()/5,-random.nextFloat()/5,random.nextFloat()/5), this, chart.getNoteEvents(i)));
        }
        /////////////////////////////////////////////////////////////////////////////////
        if (chart.getBossEvents()!=null) {
            bossMoveEvents.addAll(getBossMoveEvents(chart.getBossEvents()));
            moveEmpty = bossMoveEvents.isEmpty();

            bossRenderEvents.addAll(getBossRenderEvents(chart.getBossEvents()));
            bossrenderEmpty = bossRenderEvents.isEmpty();

            clientRenderEvents.addAll(getClientRenderEvents(chart.getClientEvents()));
            clientrenderEmpty = clientRenderEvents.isEmpty();
        }

    }
    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        //goalSelector.addGoal(1, new MoveTowardsRestrictionGoal(this, 1.0));
        //goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 90.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 1.5)
                .add(Attributes.ARMOR, 1.)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 20.0);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FAIRY_TYPE, new Random().nextInt(BossType.values().length));
    }
    @Override
    protected @NotNull PathNavigation createNavigation(Level worldIn) {
        FlyingPathNavigation navigator = new FlyingPathNavigation(this, worldIn);
        navigator.setCanOpenDoors(false);
        navigator.setCanFloat(true);
        navigator.setCanPassDoors(false);
        return navigator;
    }
    public long getTime(){
        return (System.currentTimeMillis()-time[0]);
    }

    public void tick(){
        super.tick();

        if (!this.level().isClientSide()) {
            this.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition(1.0f));
            //这里目标是boss的坐标和玩家在相对位移情况下保持同步
            if (this.originSpeed.length()==0) this.setPos(player.getX()+originPosition.x, player.getY()+originPosition.y, player.getZ()+originPosition.z);
            else this.moveTo(originSpeed.x()+player.getDeltaMovement().x(), originSpeed.y()+player.getDeltaMovement().y(), originSpeed.z()+player.getDeltaMovement().z());
            this.originPosition = new Vec3(this.getX()-player.getX(), this.getY()-player.getY(), this.getZ()-player.getZ());

            while (true){
                try {
                    if (chart.getNoteTime(index,bpm)<getTime()){
                        AbstractNote note = Objects.requireNonNull(queue.poll());
                        note.setPos(this.getX(), this.getY(), this.getZ());
                        this.level().addFreshEntity(note);
                        if (index<noteAmount-1){
                            index++;
                        }
                        else {
                            if (getTime()>songTime){
                                this.discard();
                                PacketDistributor.sendToPlayer((ServerPlayer) player,new BackToOverworldPacket(this.getHit(), this.getNoteAmount()));
                            }
                            break;
                        }
                    }else break;
                } catch (NullPointerException e) {
                    this.discard();
                }
            }
            if (!moveEmpty){
                if(bossMoveEventTemp==null)bossMoveEventTemp = bossMoveEvents.poll();
                if (bossMoveEventTemp != null) {
                    //////////////////moveTo是否有效?
                    this.moveTo(bossMoveEventTemp.onNoteMove(this));
                    if (bossMoveEventTemp.getEndtime()<getTime()) bossMoveEventTemp = null;
                }else moveEmpty = true;
            }
            if (!bossrenderEmpty){
                if(bossRenderEventTemp==null)bossRenderEventTemp = bossRenderEvents.poll();
                if (bossRenderEventTemp != null) {
                    bossRenderEventTemp.renderBoss(this);
                    if (bossRenderEventTemp.getEndtime()<getTime()) bossRenderEventTemp = null;
                }else bossrenderEmpty = true;
            }
            if (!clientrenderEmpty){
                if(clientRenderEventTemp==null)clientRenderEventTemp = clientRenderEvents.poll();
                if (clientRenderEventTemp != null) {
                    clientRenderEventTemp.renderClient(this);
                    if (clientRenderEventTemp.getEndtime()<getTime()) clientRenderEventTemp = null;
                }else clientrenderEmpty = true;
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
    public void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {

    }
    public int getFairyTypeOrdinal() {
        return this.entityData.get(FAIRY_TYPE);
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
    private List<BossMoveEvents> getBossMoveEvents(List<Map<String,Object>> events){
        return events.stream().map((event)->{
            if (event.get("type")=="change_speed") return new BossMoveEvents(BossMoveFunctions.getFunction((String) event.get("function")),(List<Double>) event.get("args"),(int)event.get("start_time"),(int)event.get("end_time"));;
            return null;
        }).toList();
    }
    private List<BossRenderEvents>getBossRenderEvents(List<Map<String,Object>> events){
        return events.stream().map((event)->{
            var classtype = event.get("category").getClass();
            if (event.get("type")=="render") return new BossRenderEvents(BossRenderFunctions.getFunction((String) event.get("function")),(List<Double>) event.get("args"),(int)event.get("start_time"),(int)event.get("end_time"));
            return null;
        }).toList();
    }
    private List<ClientRenderEvents>getClientRenderEvents(List<Map<String,Object>> events){
        return events.stream().map((event)->{
            var classtype = event.get("category").getClass();
            if (event.get("type")=="render") return new ClientRenderEvents<>(BossRenderFunctions.getFunction((String) event.get("function")),(List<Double>) event.get("args"),(int)event.get("start_time"),(int)event.get("end_time"));
            return null;
        }).toList();
    }

    public Vec3 getOriginSpeed() {
        return originSpeed;
    }

    @Override
    public boolean isFlying() {
        return true;
    }
}
