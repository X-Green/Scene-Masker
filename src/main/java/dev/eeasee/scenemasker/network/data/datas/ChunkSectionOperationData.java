package dev.eeasee.scenemasker.network.data.datas;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.world.MaskedSection;
import net.minecraft.util.PacketByteBuf;

public class ChunkSectionOperationData implements BaseData {
    private MaskedSection section;

    public ChunkSectionOperationData(MaskedSection section) {
        this.section = section;
    }

    @Override
    public void apply() {

    }

    @Override
    public void encode(PacketByteBuf packetByteBuf) {

    }
}
