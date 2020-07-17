package dev.eeasee.scenemasker.network.data;

import net.minecraft.util.PacketByteBuf;

public interface BaseData {
    void apply();
    void encode(PacketByteBuf packetByteBuf);
}
