package dev.eeasee.scenemasker.network.data.datas;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkSectionPos;

public class ChunkSectionUpdateData implements BaseData {

    private ChunkSectionPos sectionPos;

    private boolean[] values;

    private boolean isNew;


    public ChunkSectionUpdateData(boolean[] booleans, ChunkSectionPos sectionPos) {
        this.sectionPos = sectionPos;
        this.values = booleans;
    }

    public ChunkSectionUpdateData() {

    }


    @Override
    public void apply() {

    }

    @Override
    public void encode(PacketByteBuf packetByteBuf) {

    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {

    }

    @Override
    public DataType getDataType() {
        return DataType.CHUNK_SECTION_UPDATE;
    }

    @Override
    public boolean isValid() {
        return (sectionPos != null) && (values != null);
    }
}
