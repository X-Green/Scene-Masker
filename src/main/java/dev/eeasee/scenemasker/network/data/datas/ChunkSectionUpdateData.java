package dev.eeasee.scenemasker.network.data.datas;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.PacketSide;
import dev.eeasee.scenemasker.utils.Byte2Boolean;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkSectionPos;

public class ChunkSectionUpdateData implements BaseData {

    private ChunkSectionPos sectionPos;

    private boolean[] values;

    private final PacketSide SIDE;

    public ChunkSectionUpdateData(boolean[] booleans, ChunkSectionPos sectionPos, PacketSide side) {
        this.sectionPos = sectionPos;
        this.values = booleans;
        this.SIDE = side;
    }

    public ChunkSectionUpdateData(PacketSide side) {
        this.SIDE = side;
    }

    @Override
    public void apply() {

    }

    @Override
    public void encode(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(sectionPos.getSectionX());
        packetByteBuf.writeInt(sectionPos.getSectionY());
        packetByteBuf.writeInt(sectionPos.getSectionZ());
        byte[] bytes = Byte2Boolean.convertToByteArray(this.values);
        packetByteBuf.writeByteArray(bytes);
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        int x = packetByteBuf.readInt();
        int y = packetByteBuf.readInt();
        int z = packetByteBuf.readInt();
        this.sectionPos = ChunkSectionPos.from(x, y, z);
        byte[] bytes = packetByteBuf.readByteArray();
        this.values = Byte2Boolean.convertToBooleanArray(bytes);
    }

    @Override
    public DataType getDataType() {
        return DataType.CHUNK_SECTION_UPDATE;
    }

    @Override
    public PacketSide getSide() {
        return SIDE;
    }

    @Override
    public boolean isValid() {
        return (sectionPos != null) && (values != null);
    }
}
