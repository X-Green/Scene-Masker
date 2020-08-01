package dev.eeasee.scenemasker.network;

import dev.eeasee.scenemasker.Masker;
import dev.eeasee.scenemasker.network.data.IData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.s2c.*;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.PacketByteBuf;

public class CustomPayloadFactory {

    public static void handle(PacketByteBuf packetByteBuf, ClientPlayerEntity clientPlayerEntity) {
        DataType dataType = packetByteBuf.readEnumConstant(DataType.class);
        IData data = getDataObject(dataType);
        data.decode(packetByteBuf);
        data.apply(clientPlayerEntity);
    }

    public static CustomPayloadS2CPacket create(IData data) {
        if (!data.isValid()) {
            return null;
        }
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        packetByteBuf.writeEnumConstant(data.getDataType());
        data.encode(packetByteBuf);
        return new CustomPayloadS2CPacket(Masker.MASKER_CHANNEL, packetByteBuf);
    }

    private static IData getDataObject(DataType dataType) {
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
