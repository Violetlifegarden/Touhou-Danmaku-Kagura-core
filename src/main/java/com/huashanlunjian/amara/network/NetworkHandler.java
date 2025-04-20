package com.huashanlunjian.amara.network;

import com.huashanlunjian.amara.network.message.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {
    private static final String VERSION = "1.0.0";
    public static void registerPacket(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(VERSION).optional();

        registrar.executesOn(HandlerThread.MAIN).playToServer(ChangeDimensionPacket.TYPE, ChangeDimensionPacket.CODEC, ChangeDimensionPacket::handle);
        registrar.executesOn(HandlerThread.MAIN).playToServer(SongStartPacket.TYPE, SongStartPacket.CODEC, SongStartPacket::handle);
        registrar.executesOn(HandlerThread.MAIN).playToServer(BackToOverworldPacket.TYPE, BackToOverworldPacket.CODEC, BackToOverworldPacket::handle);

    }

}
