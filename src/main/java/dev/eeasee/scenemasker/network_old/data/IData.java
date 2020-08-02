package dev.eeasee.scenemasker.network_old.data;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public interface IData {
    void apply(ClientPlayerEntity playerEntity);

    void encode(PacketByteBuf packetByteBuf);

    void decode(PacketByteBuf packetByteBuf);

    DataType getDataType();

    PacketSide getSide();

    boolean isValid();
}
