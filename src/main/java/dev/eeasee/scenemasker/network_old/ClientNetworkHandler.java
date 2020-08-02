package dev.eeasee.scenemasker.network_old;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class ClientNetworkHandler {
    public static void handleData(PacketByteBuf data, ClientPlayerEntity player) {
        CustomPayloadFactory.handle(data, player);
    }

}
