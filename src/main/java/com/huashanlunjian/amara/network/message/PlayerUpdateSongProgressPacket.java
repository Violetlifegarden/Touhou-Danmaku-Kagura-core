package com.huashanlunjian.amara.network.message;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;

@Deprecated
public record PlayerUpdateSongProgressPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlayerUpdateSongProgressPacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("update_song_progress"));


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
