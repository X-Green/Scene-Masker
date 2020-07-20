package dev.eeasee.scenemasker.network.data.datas;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkSectionPos;

public class ChunkSectionUpdateData implements BaseData {

    private final DataType DATA_TYPE = DataType.CHUNK_SECTION_UPDATE;
    private ChunkSectionPos sectionPos;
    private boolean[] booleans;


    public ChunkSectionUpdateData(boolean[] booleans) {
        this.booleans = booleans;
    }


    @Override
    public void apply() {

    }

    @Override
    public void encode(PacketByteBuf packetByteBuf) {

    }
}
