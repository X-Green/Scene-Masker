package dev.eeasee.scenemasker.client;

import dev.eeasee.scenemasker.network.handler.ClientNetworkCustomHandler;
import net.fabricmc.api.ClientModInitializer;

public class MaskerClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetworkCustomHandler.registerPacketData();
    }
}
