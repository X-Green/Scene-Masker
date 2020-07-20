package dev.eeasee.scenemasker.network;

import dev.eeasee.scenemasker.Masker;
import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.datas.*;
import io.netty.buffer.Unpooled;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.PacketByteBuf;

public class CustomPayloadFactory {

    public static void handle(PacketByteBuf packetByteBuf) {
        DataType dataType = packetByteBuf.readEnumConstant(DataType.class);
        BaseData data = newDataHandler(dataType);
        data.decode(packetByteBuf);
        data.apply();
    }

    public static CustomPayloadC2SPacket create(BaseData data) {
        if (!data.isValid()) {
            return null;
        }
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        packetByteBuf.writeEnumConstant(data.getDataType());
        data.encode(packetByteBuf);
        return new CustomPayloadC2SPacket(Masker.MASKER_CHANNEL, packetByteBuf);
    }

    private static BaseData newDataHandler(DataType dataType) {
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
