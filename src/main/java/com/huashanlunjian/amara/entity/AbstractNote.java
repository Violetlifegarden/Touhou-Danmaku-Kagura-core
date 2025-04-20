package com.huashanlunjian.amara.entity;

import com.huashanlunjian.amara.api.INoteSet;
import com.huashanlunjian.amara.entity.songs.Boss;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class AbstractNote extends AbstractHurtingProjectile implements INoteSet {

    public AbstractNote(EntityType<? extends AbstractNote> entityType, Level level) {
        super(entityType, level);
    }
    public AbstractNote(EntityType<? extends AbstractNote> entityType, Level level, double x, double y, double z, Vec3 movement, Boss boss) {
        super(entityType, x, y, z,movement,level);
        this.setOwner(boss);
    }

    @Override
    public int getTime() {
        return 1000;
    }
    @Override
    protected boolean canHitEntity(Entity target) {
        return target instanceof Player;
    }


    protected boolean shouldBurn() {
        return false;
    }
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return null;
    }
}
