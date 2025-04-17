package com.huashanlunjian.amara.network.message;

import com.huashanlunjian.amara.worldgen.ModDimension;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;

public record ChangeDimensionPacket(String path,String chartPath) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ChangeDimensionPacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("change_dimension"));
    public static final StreamCodec<ByteBuf,ChangeDimensionPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ChangeDimensionPacket::path,
            ByteBufCodecs.STRING_UTF8,
            ChangeDimensionPacket::chartPath,
            ChangeDimensionPacket::new
    );
    public static void handle(ChangeDimensionPacket msg, IPayloadContext context) {
        if (context.flow().isServerbound()){
            context.enqueueWork(() -> {
                ServerPlayer player = (ServerPlayer)context.player();
                ServerLevel level = player.server.getLevel(ModDimension.AMARADIM_LEVEL_KEY);
                if (level != null){
                    player.teleportTo(level,0,-58,0,0,0);
                }
                PacketDistributor.sendToServer(new SongStartPacket(msg.path(), msg.chartPath()));

                //Minecraft.getInstance().setScreen(new SelectSongsScreen());
            });
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
