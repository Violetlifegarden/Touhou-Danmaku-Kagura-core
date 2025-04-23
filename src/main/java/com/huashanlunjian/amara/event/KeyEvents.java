



/*什么情况啊直接死机也没有任何报错，应该是有空指针？但为什么不报错*/










//package com.huashanlunjian.amara.event;
//
//import com.huashanlunjian.amara.init.keyinput.KeyRecorder;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.phys.Vec3;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.event.tick.PlayerTickEvent;
//
//@EventBusSubscriber
//public class KeyEvents {
//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public static void onClientTick(PlayerTickEvent.Pre event)
//    {
//        Player player = event.getEntity();
//        if (KeyRecorder.keyBOOTS_SKILL.isPressed() &&KeyRecorder.keySkillControl.getTickKeyDown()>0) {
//
//            player.setDeltaMovement(new Vec3(-player.getDeltaMovement().x, -player.getDeltaMovement().y, -player.getDeltaMovement().z));
//        } else if (KeyRecorder.keyLEGGING_SKILL_W.isPressed()&&KeyRecorder.keySkillControl.getTickKeyDown()>0) {
//            double xq =player.getDeltaMovement().x*player.getDeltaMovement().x;
//            double zq =player.getDeltaMovement().z*player.getDeltaMovement().z;
//            player.setDeltaMovement(new Vec3(player.getDeltaMovement().x/Math.sqrt(xq+zq)*3, player.getDeltaMovement().y, player.getDeltaMovement().z/Math.sqrt(xq+zq)*3));
//
//        }
//    }
//}
