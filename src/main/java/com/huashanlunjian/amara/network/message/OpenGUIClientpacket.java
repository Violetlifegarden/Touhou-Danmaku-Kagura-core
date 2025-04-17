package com.huashanlunjian.amara.network.message;

import com.huashanlunjian.amara.screen.gui.SelectSongsScreen;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.players.PlayerList;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;

public record OpenGUIClientpacket(BlockPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenGUIClientpacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("open_songslist_client"));
    public static final StreamCodec<ByteBuf,OpenGUIClientpacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            OpenGUIClientpacket::pos,
            OpenGUIClientpacket::new

    );
    public static void handle(OpenGUIClientpacket msg, IPayloadContext context) {
        PlayerList playerList = Minecraft.getInstance().getSingleplayerServer().getPlayerList();
        if (context.flow().isClientbound()){
            context.enqueueWork(() -> {
                Minecraft.getInstance().setScreen(new SelectSongsScreen());
            });
        }
    }



    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
