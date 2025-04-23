package com.huashanlunjian.amara.init.keyinput;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

@OnlyIn(Dist.CLIENT)

public class KeyBindings {
    private static final Options settings = Minecraft.getInstance().options;
    private static final KeyMapping SKILL_CENTERCONTROL = new KeyMapping("key.amara.SHIFT", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.amara");
    private static final KeyMapping HELMET_SKILL = new KeyMapping("key.amara.HelmetSkill", GLFW.GLFW_KEY_R, "key.categories.amara");
    private static final KeyMapping CHEST_SKILL = new KeyMapping("key.amara.ChestSkill", GLFW.GLFW_KEY_F, "key.categories.amara");
    private static final KeyMapping LEGGING_SKILL_W = new KeyMapping("key.amara.LeggingSkill_W", GLFW.GLFW_KEY_W, "key.categories.amara");
    private static final KeyMapping LEGGING_SKILL_S = new KeyMapping("key.amara.LeggingSkill_S", GLFW.GLFW_KEY_S, "key.categories.amara");
    private static final KeyMapping LEGGING_SKILL_A = new KeyMapping("key.amara.LeggingSkill_A", GLFW.GLFW_KEY_A, "key.categories.amara");
    private static final KeyMapping LEGGING_SKILL_D = new KeyMapping("key.amara.LeggingSkill_D", GLFW.GLFW_KEY_D, "key.categories.amara");
    private static final KeyMapping BOOTS_SKILL = new KeyMapping("key.amara.bootsSkill", GLFW.GLFW_KEY_SPACE, "key.categories.amara");


    private static final KeyMapping NOTE1 = new KeyMapping("key.amara.note1", GLFW_KEY_INSERT       , "key.categories.amara");
    private static final KeyMapping NOTE2 = new KeyMapping("key.amara.note2", GLFW_KEY_DELETE       , "key.categories.amara");
    private static final KeyMapping NOTE3 = new KeyMapping("key.amara.note3", GLFW_KEY_PAGE_UP      , "key.categories.amara");
    private static final KeyMapping NOTE4 = new KeyMapping("key.amara.note4", GLFW_KEY_PAGE_DOWN    , "key.categories.amara");
    private static final KeyMapping NOTE5 = new KeyMapping("key.amara.note5", GLFW_KEY_HOME         , "key.categories.amara");
    private static final KeyMapping NOTE6 = new KeyMapping("key.amara.note6", GLFW_KEY_END          , "key.categories.amara");
    private static final KeyMapping NOTE7 = new KeyMapping("key.amara.note7", GLFW.GLFW_KEY_KP_1, "key.categories.amara");
    private static final KeyMapping NOTE8 = new KeyMapping("key.amara.note8", GLFW.GLFW_KEY_KP_2, "key.categories.amara");
    private static final KeyMapping NOTE9 = new KeyMapping("key.amara.note9", GLFW.GLFW_KEY_KP_3, "key.categories.amara");
    private static final KeyMapping NOTE10 = new KeyMapping("key.amara.note10", GLFW.GLFW_KEY_KP_4, "key.categories.amara");
    private static final KeyMapping NOTE11 = new KeyMapping("key.amara.note11", GLFW.GLFW_KEY_KP_5, "key.categories.amara");
    private static final KeyMapping NOTE12 = new KeyMapping("key.amara.note12", GLFW.GLFW_KEY_KP_6, "key.categories.amara");
    private static final KeyMapping NOTE13 = new KeyMapping("key.amara.note13", GLFW.GLFW_KEY_KP_7, "key.categories.amara");
    private static final KeyMapping NOTE14 = new KeyMapping("key.amara.note14", GLFW.GLFW_KEY_KP_8, "key.categories.amara");
    private static final KeyMapping NOTE15 = new KeyMapping("key.amara.note15", GLFW.GLFW_KEY_KP_9, "key.categories.amara");
    private static final KeyMapping UP = new KeyMapping("key.amara.up", GLFW_KEY_UP            , "key.categories.amara");
    private static final KeyMapping DOWN = new KeyMapping("key.amara.down", GLFW_KEY_DOWN           , "key.categories.amara");
    private static final KeyMapping LEFT = new KeyMapping("key.amara.left", GLFW_KEY_LEFT            , "key.categories.amara");
    private static final KeyMapping RIGHT = new KeyMapping("key.amara.right", GLFW_KEY_RIGHT            , "key.categories.amara");






    public static KeyMapping getKeySKILL_CENTERCONTROL(){return SKILL_CENTERCONTROL;}
    public static KeyMapping getKeySprint() {
        return settings.keySprint;
    }

    public static KeyMapping getKeyJump() {
        return settings.keyJump;
    }

    public static KeyMapping getKeySneak() {
        return settings.keyShift;
    }

    public static KeyMapping getKeyLeft() {
        return settings.keyLeft;
    }

    public static KeyMapping getKeyRight() {
        return settings.keyRight;
    }

    public static KeyMapping getKeyForward() {
        return settings.keyUp;
    }

    public static KeyMapping getKeyBack() {
        return settings.keyDown;
    }
    public static KeyMapping getHELMET_SKILL(){
        return HELMET_SKILL;
    }
    public static KeyMapping getCHEST_SKILL(){
        return CHEST_SKILL;
    }
    public static KeyMapping getLEGGING_SKILL_W(){
        return LEGGING_SKILL_W;
    }
    public static KeyMapping getLEGGING_SKILL_S(){
        return LEGGING_SKILL_S;
    }
    public static KeyMapping getLEGGING_SKILL_A(){
        return LEGGING_SKILL_A;
    }
    public static KeyMapping getLEGGING_SKILL_D(){
        return LEGGING_SKILL_D;
    }
    public static KeyMapping getBootsSkill(){
        return BOOTS_SKILL;
    }
    public static KeyMapping getNOTE1() {
        return NOTE1;
    }
    public static KeyMapping getNOTE2() {
        return NOTE2;
    }
    public static KeyMapping getNOTE3() {
        return NOTE3;
    }
    public static KeyMapping getNOTE4() {
        return NOTE4;
    }
    public static KeyMapping getNOTE5() {
        return NOTE5;
    }
    public static KeyMapping getNOTE6() {
        return NOTE6;
    }
    public static KeyMapping getNOTE7() {
        return NOTE7;
    }
    public static KeyMapping getNOTE8() {
        return NOTE8;
    }
    public static KeyMapping getNOTE9() {
        return NOTE9;
    }
    public static KeyMapping getNOTE10() {
        return NOTE10;
    }
    public static KeyMapping getNOTE11() {
        return NOTE11;
    }
    public static KeyMapping getNOTE12() {
        return NOTE12;
    }
    public static KeyMapping getNOTE13() {
        return NOTE13;
    }
    public static KeyMapping getNOTE14() {
        return NOTE14;
    }
    public static KeyMapping getNOTE15() {
        return NOTE15;
    }
    public static KeyMapping getUP(){
        return UP;
    }
    public static KeyMapping getDOWN(){
        return DOWN;
    }
    public static KeyMapping getLEFT(){
        return LEFT;
    }
    public static KeyMapping getRIGHT(){
        return RIGHT;
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(SKILL_CENTERCONTROL);
        event.register(HELMET_SKILL);
        event.register(CHEST_SKILL);
        event.register(LEGGING_SKILL_W);
        event.register(LEGGING_SKILL_S);
        event.register(LEGGING_SKILL_A);
        event.register(LEGGING_SKILL_D);
        event.register(BOOTS_SKILL);
        event.register(NOTE1);
        event.register(NOTE2);
        event.register(NOTE3);
        event.register(NOTE4);
        event.register(NOTE5);
        event.register(NOTE6);
        event.register(NOTE7);
        event.register(NOTE8);
        event.register(NOTE9);
        event.register(NOTE10);
        event.register(NOTE11);
        event.register(NOTE12);
        event.register(NOTE13);
        event.register(NOTE14);
        event.register(NOTE15);
        event.register(UP);
        event.register(DOWN);
        event.register(LEFT);
        event.register(RIGHT);
    }
}
