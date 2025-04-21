package com.huashanlunjian.amara.network.message;

import com.huashanlunjian.amara.screen.gui.ResultScreen;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;

public record BackToOverworldPacket(int hit, int noteAmount) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<BackToOverworldPacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("back_overworld"));
    public static final StreamCodec<ByteBuf,BackToOverworldPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            BackToOverworldPacket::hit,
            ByteBufCodecs.INT,
            BackToOverworldPacket::noteAmount,
            BackToOverworldPacket::new
    );
    public static void handle(BackToOverworldPacket msg, IPayloadContext context) {
        if (context.flow().isClientbound()){
            context.enqueueWork(() -> {
                LocalPlayer player = (LocalPlayer) context.player();
                Minecraft.getInstance().setScreen(new ResultScreen( () -> player.connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN)),msg.hit,msg.noteAmount));
            });
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
