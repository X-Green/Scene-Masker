package dev.eeasee.scenemasker.network;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.datas.MultiBlockOperationData;
import dev.eeasee.scenemasker.network.data.datas.SettingsData;
import net.minecraft.util.PacketByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DataParser {
    private final static Map<DataType, Consumer<PacketByteBuf>> PACKET_CONSUMER_MAP = new HashMap<DataType, Consumer<PacketByteBuf>>(){{
        put(DataType.MULTI_BLOCK_OPERATION, (packetByteBuf -> MultiBlockOperationData.decode(packetByteBuf).apply()));
        put(DataType.SETTINGS, (packetByteBuf -> SettingsData.decode(packetByteBuf).apply()));
    }};

    public static void handlePacket(PacketByteBuf packetByteBuf) {
        DataType dataType = packetByteBuf.readEnumConstant(DataType.class);
        PACKET_CONSUMER_MAP.get(dataType).accept(packetByteBuf);
    }
}
