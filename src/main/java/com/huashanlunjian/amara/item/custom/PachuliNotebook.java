package com.huashanlunjian.amara.item.custom;

import com.huashanlunjian.amara.screen.gui.SelectSongsScreen;
import com.huashanlunjian.amara.worldgen.ModDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PachuliNotebook extends Item {
    public PachuliNotebook(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if (!level.dimension().equals(ModDimension.AMARADIM_LEVEL_KEY)&& level.isClientSide()) {
            Minecraft.getInstance().setScreen(new SelectSongsScreen());
        }
        ItemStack itemstack = player.getItemInHand(hand);
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


}
