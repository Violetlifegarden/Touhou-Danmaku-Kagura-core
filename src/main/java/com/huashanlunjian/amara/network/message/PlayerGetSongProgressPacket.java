package com.huashanlunjian.amara.network.message;

import com.huashanlunjian.amara.entity.songs.Boss;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;

@Deprecated
public record PlayerGetSongProgressPacket(UUID uuid) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlayerGetSongProgressPacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("get_song_progress"));
    public static final StreamCodec<ByteBuf,PlayerGetSongProgressPacket> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            PlayerGetSongProgressPacket::uuid,
            PlayerGetSongProgressPacket::new
    );
    public static void handle(PlayerGetSongProgressPacket packet, IPayloadContext context){
        if (context.flow().isServerbound()){
            context.enqueueWork(() -> {
                ServerPlayer player = (ServerPlayer) context.player();
                Boss boss = (Boss)player.serverLevel().getEntity(packet.uuid());
                if (boss != null){
                    boss.startSeenByPlayer(player);
                }

            });
        }
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
