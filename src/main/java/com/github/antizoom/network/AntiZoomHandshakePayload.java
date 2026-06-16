package com.github.antizoom.network;

import com.github.antizoom.AntiZoomConstants;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AntiZoomHandshakePayload() implements CustomPayload {
    public static final CustomPayload.Id<AntiZoomHandshakePayload> ID = new CustomPayload.Id<>(Identifier.of(AntiZoomConstants.MOD_ID, "installed"));
    public static final PacketCodec<RegistryByteBuf, AntiZoomHandshakePayload> CODEC = PacketCodec.unit(new AntiZoomHandshakePayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
