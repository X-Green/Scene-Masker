package dev.eeasee.scenemasker.network.data.s2c;

import dev.eeasee.scenemasker.Masker;
import dev.eeasee.scenemasker.fakes.ChunkSectionInterface;
import dev.eeasee.scenemasker.utils.BooleanUtils;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

public class ChunkUpdateData implements IDataS2C {
    private ChunkPos chunkPos;

    private byte[][] sectionBytesArrays = new byte[16][];

    private boolean[] sectionsToUpdate;

    public ChunkUpdateData(Chunk chunk) {
        if (chunk == null) {
            return;
        }
        chunkPos = chunk.getPos();
        sectionsToUpdate = new boolean[16];
        ChunkSection[] sections = chunk.getSectionArray();
        for (int i = 0; i < 16; i++) {
            ChunkSectionInterface sectionInterface = (ChunkSectionInterface) sections[i];
            if (sectionInterface.isMaskerChanged()) {
                sectionsToUpdate[i] = true;
                sectionBytesArrays[i] = sectionInterface.toByteArray();
            }
        }
        if (!BooleanUtils.or(sectionsToUpdate)) {
            sectionsToUpdate = null;
        }
    }

    public ChunkUpdateData() {
    }

    @Override
    public void apply(ClientPlayerEntity clientPlayerEntity) {
        if (chunkPos == null || sectionsToUpdate == null || sectionBytesArrays == null) {
            Masker.LOGGER.warn("(ChunkUpdateData for Masker) Invalid Chunk Update Data Applied To Client World");
            return;
        }
        ChunkSection[] sections = clientPlayerEntity.world.getChunk(chunkPos.x, chunkPos.z).getSectionArray();
        ChunkSectionInterface sectionInterface;
        for (int i = 0; i < 16; i++) {
            if (sectionsToUpdate[i]) {
                sectionInterface = (ChunkSectionInterface) sections[i];
                byte[] bytes = sectionBytesArrays[i];
                sectionInterface.setMaskerStates((bytes == null) ? null : BooleanUtils.convertToBooleanArray(bytes));
            }
        }
    }

    @Override
    public PacketByteBuf encode() {
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        if (sectionsToUpdate == null || chunkPos == null) {
            packetByteBuf.writeShort(-1);
            return packetByteBuf;
        }
        for (int i = 0; i < 16; i++) {
            if (sectionsToUpdate[i]) {
                byte[] bytes = sectionBytesArrays[i];
                if (bytes == null) {
                    packetByteBuf.writeBoolean(false);
                } else {
                    packetByteBuf.writeBoolean(true);
                    packetByteBuf.writeByteArray(bytes);
                }
            }
        }
        packetByteBuf.writeShort(16);
        packetByteBuf.writeInt(chunkPos.x);
        packetByteBuf.writeInt(chunkPos.z);
        return packetByteBuf;
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        while (true) {
            int index = packetByteBuf.readShort();
            if (index < 0) {
                break;
            }
            if (index > 16) {
                int chunkPosX = packetByteBuf.readInt();
                int chunkPosZ = packetByteBuf.readInt();
                this.chunkPos = new ChunkPos(chunkPosX, chunkPosZ);
                break;
            }
            sectionsToUpdate[index] = true;
            boolean hasContent = packetByteBuf.readBoolean();
            if (hasContent) {
                sectionBytesArrays[index] = packetByteBuf.readByteArray();
            } else {
                sectionBytesArrays[index] = null;
            }
        }
    }
}
