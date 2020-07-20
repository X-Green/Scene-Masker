package dev.eeasee.scenemasker.network.data.datas;

import com.google.common.collect.Sets;
import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class MultiBlockUpdateData implements BaseData {

    private Set<BlockPos> blockPosSet;
    private final DataType DATA_TYPE = DataType.MULTI_BLOCK_UPDATE;

    public MultiBlockUpdateData(Set<BlockPos> blockPosSet) {
        this.blockPosSet = blockPosSet;
    }

    @Override
    public void apply() {
    }

    @Override
    public void encode(PacketByteBuf packetBuf) {
        packetBuf.writeInt(blockPosSet.size());
        blockPosSet.forEach((packetBuf::writeBlockPos));
    }

    public static MultiBlockUpdateData decode(PacketByteBuf packetByteBuf) {
        int size = packetByteBuf.readInt();
        Set<BlockPos> blockPosSet1 = Sets.newHashSet();
        for (int i = 0; i < size; i++) {
            blockPosSet1.add(
                    packetByteBuf.readBlockPos()
            );
        }
        return new MultiBlockUpdateData(blockPosSet1);
    }

}
