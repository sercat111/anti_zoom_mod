package com.github.antizoom.server;

import com.github.antizoom.network.AntiZoomHandshakePayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;

public final class AntiZoomMod implements ModInitializer {
    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) {
            return;
        }

        PayloadTypeRegistry.playS2C().register(AntiZoomHandshakePayload.ID, AntiZoomHandshakePayload.CODEC);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (!ServerPlayNetworking.canSend(handler.player, AntiZoomHandshakePayload.ID)) {
                handler.disconnect(Text.translatable("disconnect.anti_zoom_mod.required"));
                return;
            }
            sender.sendPacket(new AntiZoomHandshakePayload());
        });
    }
}
