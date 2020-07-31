package dev.eeasee.scenemasker.network.data.s2c;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.PacketSide;
import dev.eeasee.scenemasker.utils.Byte2Boolean;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;

public class ChunkSectionUpdateData implements BaseData {

    private ChunkSectionPos sectionPos;

    private boolean[] values;

    public ChunkSectionUpdateData(boolean[] booleans, ChunkSectionPos sectionPos) {
        this.sectionPos = sectionPos;
        this.values = booleans;
    }

    public ChunkSectionUpdateData() {

    }

    @Override
    public void apply() {
        World world = MinecraftClient.getInstance().world;
        if (world == null) {
            return;
        }
        MaskedWorld worldMasker = ((WorldInterface)world).getWorldMasker();
        worldMasker.setSectionMasked(this.sectionPos, this.values);
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
        return PacketSide.SERVER_TO_CLIENT;
    }

    @Override
    public boolean isValid() {
        return (sectionPos != null) && (values != null);
    }
}