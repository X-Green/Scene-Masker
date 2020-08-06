package dev.eeasee.scenemasker.network.data.s2c;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public interface IDataS2C {
    void apply(ClientPlayerEntity playerEntity);

    PacketByteBuf encode();

    void decode(PacketByteBuf packetByteBuf);

    static void consume(PacketContext context, PacketByteBuf buffer, IDataS2C data) {
        data.decode(buffer);
        if (context.getPacketEnvironment() == EnvType.CLIENT) {
            context.getTaskQueue().execute(() -> data.apply((ClientPlayerEntity) context.getPlayer()));
        }
    }
}
