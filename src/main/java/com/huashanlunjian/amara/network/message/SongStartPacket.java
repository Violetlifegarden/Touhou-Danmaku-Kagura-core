package com.huashanlunjian.amara.network.message;

import com.huashanlunjian.amara.entity.songs.Boss;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.huashanlunjian.amara.utils.FileUtil.getResourceLocation;

public record SongStartPacket(String path,String chartPath)implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SongStartPacket> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("songs_start"));
    public static final StreamCodec<ByteBuf,SongStartPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SongStartPacket :: path,
            ByteBufCodecs.STRING_UTF8,
            SongStartPacket :: chartPath,
            SongStartPacket::new
    );

    public static void handle(SongStartPacket msg, IPayloadContext context){

        if (context.flow().isServerbound()){
            context.enqueueWork(() -> {
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Boss boss = new Boss(context.player(),msg.chartPath(), audioFile(msg.path));
                        TimeUnit.MILLISECONDS.sleep(3000);
                        boss.level().addFreshEntity(boss);
                    } catch (InterruptedException ignored) {
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                             IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                    //boss.setCustomName(boss.getDisplayName());

                    return null;
                });
            });
        }
    }
//    public static AbstractChart chart(String chartPath){
//        return ChartUtil.loadChart(Path.of(chartPath));
//    }

    @Deprecated
    public static Path audioFile(String resource) {
        return Path.of(resource);
    }

    @Deprecated
    public static Path resourcePath(String resource) {
        Path path = Path.of(resource);
        return path.resolve(resource);
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
