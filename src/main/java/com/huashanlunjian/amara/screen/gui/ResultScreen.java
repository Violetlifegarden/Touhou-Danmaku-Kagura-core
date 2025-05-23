package com.huashanlunjian.amara.screen.gui;

import com.huashanlunjian.amara.entity.songs.Boss;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ResultScreen extends Screen {
    private static Component SECTION_HEADING; //= Component.literal("音游总要有一个Result界面，暂未实现\n按esc键退出").withStyle(ChatFormatting.WHITE);
    private final Runnable onFinished;


    public ResultScreen(Runnable onFinished, int height,int noteAmount) {
        super(Component.literal("ResultScreen"));
        this.onFinished = onFinished;
        this.minecraft= Minecraft.getInstance();
        this.font = Minecraft.getInstance().font;
        String s = "被打到的次数:"+height+"总弹幕数"+noteAmount;
        SECTION_HEADING = Component.literal(s).withStyle(ChatFormatting.WHITE);
    }
    @Override
    public void onClose(){
        super.onClose();
        //onFinished.run();
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, SECTION_HEADING, this.width / 2, 8, 16777215);
    }
}
