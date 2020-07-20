package dev.eeasee.scenemasker.network;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.datas.*;
import net.minecraft.util.PacketByteBuf;

public class DataParser {

    public static void handlePacket(PacketByteBuf packetByteBuf) {
        DataType dataType = packetByteBuf.readEnumConstant(DataType.class);
    }

    private static BaseData getDataHandler(DataType dataType) {
        switch (dataType) {
            case SETTINGS:
                return new SettingsData();
            case MULTI_BLOCK_UPDATE:
                return new MultiBlockUpdateData();
            case CHUNK_SECTION_UPDATE:
                return new ChunkSectionUpdateData();
            default:
                return new DummyData();
        }
    }
}
