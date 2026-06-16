package com.github.antizoom.client;

import com.github.antizoom.network.AntiZoomHandshakePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public final class AntiZoomClient implements ClientModInitializer {
    private static boolean connectedServerHasAntiZoom;

    @Override
    public void onInitializeClient() {
        PayloadTypeRegistry.playS2C().register(AntiZoomHandshakePayload.ID, AntiZoomHandshakePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(AntiZoomHandshakePayload.ID, (payload, context) -> connectedServerHasAntiZoom = true);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> connectedServerHasAntiZoom = false);
    }

    public static boolean connectedServerHasAntiZoom() {
        return connectedServerHasAntiZoom;
    }
}
