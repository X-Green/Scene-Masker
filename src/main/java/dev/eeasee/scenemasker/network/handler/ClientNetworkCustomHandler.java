package dev.eeasee.scenemasker.network.handler;

import dev.eeasee.scenemasker.network.MaskerIdentifiers;
import dev.eeasee.scenemasker.network.packet.s2c.ChunkUpdateS2C;
import dev.eeasee.scenemasker.network.packet.s2c.IDataS2C;
import dev.eeasee.scenemasker.network.packet.s2c.MultiBlockUpdateS2C;
import dev.eeasee.scenemasker.network.packet.s2c.ConfigsS2C;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class ClientNetworkCustomHandler {
    public static void registerPacketData() {
        ClientSidePacketRegistry.INSTANCE.register(MaskerIdentifiers.CHUNK_UPDATE_S2C, ((context, buffer) -> IDataS2C.consume(context, buffer, new ChunkUpdateS2C())));
        ClientSidePacketRegistry.INSTANCE.register(MaskerIdentifiers.MULTI_BLOCK_UPDATE_S2C, ((context, buffer) -> IDataS2C.consume(context, buffer, new MultiBlockUpdateS2C())));
        ClientSidePacketRegistry.INSTANCE.register(MaskerIdentifiers.CONFIGS_S2C, ((context, buffer) -> IDataS2C.consume(context, buffer, new ConfigsS2C())));
    }
}
