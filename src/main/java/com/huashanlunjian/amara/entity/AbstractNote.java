package com.huashanlunjian.amara.entity;

import com.huashanlunjian.amara.api.INoteSet;
import com.huashanlunjian.amara.entity.songs.Boss;
import com.huashanlunjian.amara.music_game_extension.NoteMoveFunctions;
import com.huashanlunjian.amara.music_game_extension.NoteRenderFunctions;
import com.huashanlunjian.amara.music_game_extension.events.NoteMoveEvents;
import com.huashanlunjian.amara.music_game_extension.events.NoteRenderEvents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class AbstractNote extends AbstractHurtingProjectile implements INoteSet {
    private boolean noteMoveTag = false;
    private NoteMoveEvents noteMoveEvents;

    public AbstractNote(EntityType<? extends AbstractNote> entityType, Level level) {
        super(entityType, level);
    }
    public AbstractNote(EntityType<? extends AbstractNote> entityType, Level level, double x, double y, double z, Vec3 movement, Boss boss, List<Map<String, Object>> events) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(entityType, x, y, z,movement,level);
        this.setOwner(boss);
        if (events!=null) {
            for (NoteRenderEvents event : parseRenderEvents(events)){
                NoteRenderFunctions.class.getMethod(event.functionName(), AbstractNote.class).invoke(NoteRenderFunctions.class.getDeclaredConstructor().newInstance(),this);
            }
            parseRenderEvents(events);
        }
    }
    /**这里写的有问题，tick没有把tap现有的速度给传进去，
     * 实际上这是很有必要的*/
    public void tick() {
        super.tick();
        if (noteMoveTag){
            this.moveTo(noteMoveEvents.onNoteMove());
        }
    }

    @Override
    public int getTime() {
        return 1000;
    }

    @Override
    public List<NoteRenderEvents> parseRenderEvents(List<Map<String, Object>> events) {
        return events.stream().map((event)->{
            //return new NoteMoveEvents(event);
            if (event.get("type")=="render") return new NoteRenderEvents((String) event.get("function"));;
            return null;
        }).toList();
    }

    @Override
    public void parseNoteMoveEvent(List<Map<String, Object>> events) {
        for (Map<String, Object> event : events){
            if (event.get("type")=="change_speed"){
                noteMoveTag = true;
                this.noteMoveEvents = new NoteMoveEvents(NoteMoveFunctions.getFunction((String) event.get("function")), (List<Double>) event.get("args"));
            }
        }
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
