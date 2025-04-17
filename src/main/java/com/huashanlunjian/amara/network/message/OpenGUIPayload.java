package com.huashanlunjian.amara.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;
@Deprecated
/**已经确定这个类没用了，之后会删除
 * 实际上不需要从这里获取serverPlayer*/
public record OpenGUIPayload(double x,double y,double z) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenGUIPayload> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("open_songslist"));
    public static final StreamCodec<ByteBuf,OpenGUIPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            OpenGUIPayload::x,
            ByteBufCodecs.DOUBLE,
            OpenGUIPayload::y,
            ByteBufCodecs.DOUBLE,
            OpenGUIPayload::z,
            OpenGUIPayload::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handle(OpenGUIPayload msg, IPayloadContext context) {
        if (context.flow().isServerbound()){
            context.enqueueWork(() -> {
                System.out.println("OpenGUIPayload");
                //Minecraft.getInstance().setScreen(new SelectSongsScreen());
            });
        }
    }
}
