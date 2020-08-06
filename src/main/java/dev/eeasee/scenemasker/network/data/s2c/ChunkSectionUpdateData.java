package dev.eeasee.scenemasker.network.data.s2c;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.utils.Byte2Boolean;
import dev.eeasee.scenemasker.world.MaskedSection;
import dev.eeasee.scenemasker.world.MaskedWorld;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkSectionPos;

public class ChunkSectionUpdateData implements IDataS2C {

    private ChunkSectionPos sectionPos;

    private boolean[] values;

    public ChunkSectionUpdateData(boolean[] booleans, ChunkSectionPos sectionPos) {
        this.sectionPos = sectionPos;
        this.values = booleans;
    }

    public ChunkSectionUpdateData() {

    }

    public ChunkSectionUpdateData(MaskedSection section){
        this.sectionPos = section.getSectionPos();
        this.values = section.getBooleansArrayCopied();
    }

    @Override
    public void apply(ClientPlayerEntity clientPlayerEntity) {
        ClientWorld world = clientPlayerEntity.clientWorld;
        MaskedWorld worldMasker = ((WorldInterface)world).getWorldMasker();
        worldMasker.setSectionMasked(this.sectionPos, this.values);
        worldMasker.deleteChunkIfEmpty(this.sectionPos.toChunkPos());
    }

    @Override
    public PacketByteBuf encode() {
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        packetByteBuf.writeInt(sectionPos.getSectionX());
        packetByteBuf.writeInt(sectionPos.getSectionY());
        packetByteBuf.writeInt(sectionPos.getSectionZ());
        if (isBooleansAllFalse(this.values)) {
            packetByteBuf.writeBoolean(false);
        } else {
            packetByteBuf.writeBoolean(true);
            byte[] bytes = Byte2Boolean.convertToByteArray(this.values);
            packetByteBuf.writeByteArray(bytes);
        }
        return packetByteBuf;
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        int x = packetByteBuf.readInt();
        int y = packetByteBuf.readInt();
        int z = packetByteBuf.readInt();
        this.sectionPos = ChunkSectionPos.from(x, y, z);
        if (packetByteBuf.readBoolean()) {
            byte[] bytes = packetByteBuf.readByteArray();
            this.values = Byte2Boolean.convertToBooleanArray(bytes);
        } else {
            this.values = new boolean[4096];
        }
    }

    private static boolean isBooleansAllFalse(boolean[] bools) {
        boolean hasTrue = false;
        for (boolean bool : bools) {
            hasTrue |= bool;
        }
        return hasTrue;
    }
}
