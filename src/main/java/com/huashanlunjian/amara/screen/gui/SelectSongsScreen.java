package com.huashanlunjian.amara.screen.gui;

import com.huashanlunjian.amara.music_game_core.SongsSelectionList;
import com.huashanlunjian.amara.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;

public class SelectSongsScreen extends Screen {

    private Button deleteButton;
    private Button selectButton;
    private Button editButton;
    protected EditBox searchBox;
    public SongsSelectionList list;



    public SelectSongsScreen() {
        super(Component.literal("SongsSelection"));
        this.minecraft = Minecraft.getInstance();
        this.list = new SongsSelectionList(this, this.minecraft, this.width, this.height-112, 48, 36, "", this.list);

    }
    @Override
    protected void init() {
        this.searchBox = new EditBox(this.font, this.width / 2 - 100, 22, 200, 20, this.searchBox, Component.translatable("selectWorld.search"));
        //this.searchBox.setResponder(p_232980_ -> this.list.updateFilter(p_232980_));
        this.list = this.addRenderableWidget(
                new SongsSelectionList(this, this.minecraft, this.width, this.height - 112, 48, 36, this.searchBox.getValue(), this.list)
        );
        this.addWidget(this.searchBox);
        //this.updateButtonStatus(null);
        this.selectButton = this.addRenderableWidget(
                Button.builder(LevelSummary.PLAY_WORLD, p_232984_ -> {
                        if (list.getSelectedOpt().isPresent()) {
                            MiscUtils.enterMusicGame(list.getSelectedOpt().get().getSummary(), this.minecraft.player);
                        }
                        })
                        .bounds(this.width / 2 - 154, this.height - 52, 150, 20)
                        .build()
        );
        this.editButton = this.addRenderableWidget(
                Button.builder(
                                Component.translatable("selectWorld.edit"), p_101378_ -> System.out.println("编辑模式（暂未实现）")
                        )
                        .bounds(this.width / 2 - 154, this.height - 28, 72, 20)
                        .build()
        );
        this.deleteButton = this.addRenderableWidget(
                Button.builder(
                                Component.translatable("selectWorld.delete"),
                                p_101376_ -> System.out.println("删除歌曲（暂未实现）")
                        )
                        .bounds(this.width / 2 - 76, this.height - 28, 72, 20)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.searchBox.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
    }
    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(this.searchBox);
    }
    @Override
    public void removed() {
        //if (this.list != null) {
        //    this.list.children().forEach(WorldSelectionList.Entry::close);
        //}
    }

}
