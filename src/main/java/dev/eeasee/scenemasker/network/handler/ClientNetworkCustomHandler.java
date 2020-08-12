package dev.eeasee.scenemasker.network.handler;

import dev.eeasee.scenemasker.network.MaskerIdentifiers;
import dev.eeasee.scenemasker.network.data.s2c.ChunkUpdateData;
import dev.eeasee.scenemasker.network.data.s2c.IDataS2C;
import dev.eeasee.scenemasker.network.data.s2c.MultiBlockUpdateData;
import dev.eeasee.scenemasker.network.data.s2c.SettingsData;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class ClientNetworkCustomHandler {
    public static void registerPacketData() {
        ClientSidePacketRegistry.INSTANCE.register(MaskerIdentifiers.CHUNK_DATA_ID, ((context, buffer) -> IDataS2C.consume(context, buffer, new ChunkUpdateData())));
        ClientSidePacketRegistry.INSTANCE.register(MaskerIdentifiers.MULTI_BLOCK_CHANGE_ID, ((context, buffer) -> IDataS2C.consume(context, buffer, new MultiBlockUpdateData())));
        ClientSidePacketRegistry.INSTANCE.register(MaskerIdentifiers.SETTINGS, ((context, buffer) -> IDataS2C.consume(context, buffer, new SettingsData())));
    }
}
