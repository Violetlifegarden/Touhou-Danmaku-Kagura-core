package com.huashanlunjian.amara.entity;

import com.huashanlunjian.amara.entity.songs.Boss;
import com.huashanlunjian.amara.init.InitEntities;
import com.huashanlunjian.amara.music_game_extension.DanmakuColor;
import com.huashanlunjian.amara.music_game_extension.DanmakuType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class Tap extends AbstractNote {

    private static final EntityDataAccessor<Integer> DANMAKU_TYPE = SynchedEntityData.defineId(Tap.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Tap.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Tap.class, EntityDataSerializers.FLOAT);
    private Vec3 originMovement;


    public Tap(EntityType<? extends Tap> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**一共有2个方法*/
    public Tap(Level level, double x, double y, double z, Vec3 movement, Boss boss, List<Map<String, Object>> events) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(InitEntities.TAP.get(),level,x, y, z, movement,boss, events);
        this.originMovement = movement;
    }
    public void tick(){
        super.tick();
        //这里处理事件

        try {
            if (distanceTo(this.getOwner())>=100)this.discard();
        }catch (NullPointerException e){
            this.discard();
        }

    }
    protected void onHitEntity(EntityHitResult result){
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof Player) {
            entity.hurt(this.damageSources().thrown(this, this), 2.0F);
            ((Boss)this.getOwner()).onHit();
            this.discard();
        }
    }


    public Vec3 getOriginMovement() {
        return originMovement;
    }
    @Override
    public void onAddedToLevel() {
    }
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DANMAKU_TYPE, DanmakuType.random.nextInt(DanmakuType.getLength()));
        builder.define(COLOR, DanmakuColor.random.nextInt(DanmakuColor.getLength()));
        builder.define(DAMAGE, 1.0f);
    }
    public DanmakuType getDanmakuType() {
        return DanmakuType.getType(this.entityData.get(DANMAKU_TYPE));
    }

    public Tap setDanmakuType(DanmakuType type) {
        this.entityData.set(DANMAKU_TYPE, type.ordinal());
        return this;
    }

    public DanmakuColor getColor() {
        return DanmakuColor.getColor(this.entityData.get(COLOR));
    }

    public Tap setColor(DanmakuColor color) {
        this.entityData.set(COLOR, color.ordinal());
        return this;
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    public Tap setDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
        return this;
    }

}
