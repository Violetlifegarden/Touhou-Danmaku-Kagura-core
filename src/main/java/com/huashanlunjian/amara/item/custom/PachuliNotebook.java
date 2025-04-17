package com.huashanlunjian.amara.item.custom;

import com.huashanlunjian.amara.network.message.OpenGUIPayload;
import com.huashanlunjian.amara.screen.gui.SelectSongsScreen;
import com.huashanlunjian.amara.worldgen.ModDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

/*这里有个问题，如果刚进游戏打开GUI，会出现一个NullPointerException，
因为玩家刚进游戏，其中的某个Minecraft实例为null，
但不是Minecraft.getInstance()
* */


public class PachuliNotebook extends Item {
    public PachuliNotebook(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if (!level.dimension().equals(ModDimension.AMARADIM_LEVEL_KEY)&& level.isClientSide()) {
            //ServerPlayer serverPlayer = pla
            //player.getUUID()
            //PacketDistributor.sendToServer(new OpenGUIPayload(player.getX(),player.getY(),player.getZ()));
            Minecraft.getInstance().setScreen(new SelectSongsScreen());
        }
        ItemStack itemstack = player.getItemInHand(hand);
//        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
//            // 发送数据包到客户端
//            NetworkRegistry.OpenGuiPayload payload = new NetworkRegistry.OpenGuiPayload();
//            serverPlayer.connection.send(payload);
//        }

        //player.openItemGui(itemstack, hand);
        //((ServerPlayer)player).connection.send(new ClientboundOpenBookPacket(hand));
        //player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


}
