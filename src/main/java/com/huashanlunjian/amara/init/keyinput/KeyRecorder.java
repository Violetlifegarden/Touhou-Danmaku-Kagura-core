package com.huashanlunjian.amara.init.keyinput;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;


@EventBusSubscriber
public class KeyRecorder {
    public static final KeyState keySkillControl = new KeyState();
    public static final KeyState keyForward = new KeyState();
    public static final KeyState keyBack = new KeyState();
    public static final KeyState keyRight = new KeyState();
    public static final KeyState keyLeft = new KeyState();
    public static final KeyState keySneak = new KeyState();
    public static final KeyState keyJumpState = new KeyState();
    public static final KeyState keySprintState = new KeyState();
    public static final KeyState keyHELMET_SKILL = new KeyState();
    public static final KeyState keyCHEST_SKILL = new KeyState();
    public static final KeyState keyLEGGING_SKILL_W = new KeyState();
    public static final KeyState keyLEGGING_SKILL_S = new KeyState();
    public static final KeyState keyLEGGING_SKILL_A = new KeyState();
    public static final KeyState keyLEGGING_SKILL_D = new KeyState();
    public static final KeyState keyBOOTS_SKILL = new KeyState();
    public static final KeyState keyNOTE1 = new KeyState();
    public static final KeyState keyNOTE2 = new KeyState();
    public static final KeyState keyNOTE3 = new KeyState();
    public static final KeyState keyNOTE4 = new KeyState();
    public static final KeyState keyNOTE5 = new KeyState();
    public static final KeyState keyNOTE6 = new KeyState();
    public static final KeyState keyNOTE7 = new KeyState();
    public static final KeyState keyNOTE8 = new KeyState();
    public static final KeyState keyNOTE9 = new KeyState();
    public static final KeyState keyNOTE10 = new KeyState();
    public static final KeyState keyNOTE11 = new KeyState();
    public static final KeyState keyNOTE12 = new KeyState();
    public static final KeyState keyNOTE13 = new KeyState();
    public static final KeyState keyNOTE14 = new KeyState();
    public static final KeyState keyNOTE15 = new KeyState();
    public static final KeyState keyUP = new KeyState();
    public static final KeyState keyDOWN = new KeyState();
    public static final KeyState keyLEFT = new KeyState();
    public static final KeyState keyRIGHT = new KeyState();



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event){

        record(KeyBindings.getKeySKILL_CENTERCONTROL(),keySkillControl);
        record(KeyBindings.getKeyForward(), keyForward);
        record(KeyBindings.getKeyBack(), keyBack);
        record(KeyBindings.getKeyRight(), keyRight);
        record(KeyBindings.getKeyLeft(), keyLeft);
        record(KeyBindings.getKeySneak(), keySneak);
        record(KeyBindings.getKeyJump(), keyJumpState);
        record(KeyBindings.getKeySprint(), keySprintState);
        record(KeyBindings.getHELMET_SKILL(), keyHELMET_SKILL);
        record(KeyBindings.getCHEST_SKILL(), keyCHEST_SKILL);
        record(KeyBindings.getLEGGING_SKILL_W(), keyLEGGING_SKILL_W );
        record(KeyBindings.getLEGGING_SKILL_S(), keyLEGGING_SKILL_S);
        record(KeyBindings.getLEGGING_SKILL_A(), keyLEGGING_SKILL_A);
        record(KeyBindings.getLEGGING_SKILL_D(), keyLEGGING_SKILL_D);
        record(KeyBindings.getBootsSkill(), keyBOOTS_SKILL);
        record(KeyBindings.getNOTE1(), keyNOTE1);
        record(KeyBindings.getNOTE2(), keyNOTE2);
        record(KeyBindings.getNOTE3(), keyNOTE3);
        record(KeyBindings.getNOTE4(), keyNOTE4);
        record(KeyBindings.getNOTE5(), keyNOTE5);
        record(KeyBindings.getNOTE6(), keyNOTE6);
        record(KeyBindings.getNOTE7(), keyNOTE7);
        record(KeyBindings.getNOTE8(), keyNOTE8);
        record(KeyBindings.getNOTE9(), keyNOTE9);
        record(KeyBindings.getNOTE10(), keyNOTE10);
        record(KeyBindings.getNOTE11(), keyNOTE11);
        record(KeyBindings.getNOTE12(), keyNOTE12);
        record(KeyBindings.getNOTE13(), keyNOTE13);
        record(KeyBindings.getNOTE14(), keyNOTE14);
        record(KeyBindings.getNOTE15(), keyNOTE15);
        record(KeyBindings.getUP(), keyUP);
        record(KeyBindings.getDOWN(), keyDOWN);
        record(KeyBindings.getLEFT(), keyLEFT);
        record(KeyBindings.getRIGHT(), keyRIGHT);

        }
    private static void record(KeyMapping keyBinding, KeyState state) {
        state.pressed = (keyBinding.isDown() && state.tickKeyDown == 0);
        state.released = (!keyBinding.isDown() && state.tickNotKeyDown == 0);
        state.doubleTapped = (keyBinding.isDown() && 0 < state.tickNotKeyDown && state.tickNotKeyDown <= 2);
        if (keyBinding.isDown()) {
            state.tickKeyDown++;
            state.tickNotKeyDown = 0;
        } else {
            state.tickKeyDown = 0;
            if (state.tickNotKeyDown<500){
                state.tickNotKeyDown++;
            }
        }
    }
    private int area(int x,int y){
        return x*y;
    }


    public static class KeyState {
        private boolean pressed = false;
        private boolean released = false;
        private boolean doubleTapped = false;
        private int tickKeyDown = 0;
        private int tickNotKeyDown = 0;

        public boolean isPressed() {
            return pressed;
        }

        public boolean isReleased() {
            return released;
        }

        public boolean isDoubleTapped() {
            return doubleTapped;
        }

        public int getTickKeyDown() {
            return tickKeyDown;
        }

        public int getTickNotKeyDown() {
            return tickNotKeyDown;
        }
    }
}
