package com.huashanlunjian.amara.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;
import static net.minecraft.world.level.Level.OVERWORLD;

public record BackToOverworldPacket(Boolean back) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<BackToOverworldPacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("back_overworld"));
    public static final StreamCodec<ByteBuf,BackToOverworldPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            BackToOverworldPacket::back,
            BackToOverworldPacket::new
    );
    public static void handle(BackToOverworldPacket msg, IPayloadContext context) {
        if (context.flow().isServerbound()){
            context.enqueueWork(() -> {
                ServerPlayer player = (ServerPlayer)context.player();
                ServerLevel level = player.server.getLevel(OVERWORLD);
                if (level != null){
                    player.changeDimension(player.portalProcess.getPortalDestination(level, player));
                }
                //Minecraft.getInstance().setScreen(new SelectSongsScreen());
            });
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
