package com.huashanlunjian.amara.network.message;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;

@Deprecated
public record PlaySongsRequestPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlaySongsRequestPacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("songs_request"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
