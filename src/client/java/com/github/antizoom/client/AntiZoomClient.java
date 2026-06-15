package com.github.antizoom.client;

import net.fabricmc.api.ClientModInitializer;

public final class AntiZoomClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // All zoom protection is applied through the GameRenderer mixin.
    }
}
