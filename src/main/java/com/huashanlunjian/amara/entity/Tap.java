package com.huashanlunjian.amara.entity;

import com.huashanlunjian.amara.api.INoteSet;
import com.huashanlunjian.amara.entity.songs.Boss;
import com.huashanlunjian.amara.init.InitEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class Tap extends AbstractNote {
    private Vec3 originposition;

    public Tap(EntityType<? extends Tap> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public Tap(Level pLevel,Vec3 originposition) {
        super(InitEntities.TAP.get(), pLevel);
        this.originposition = originposition;
        this.setPos(originposition);
    }
    public Tap(Level level, double x, double y, double z, Vec3 movement, Boss boss) {
        super(InitEntities.TAP.get(),level,x, y, z, movement,boss);
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
            this.discard();
        }
    }




}
