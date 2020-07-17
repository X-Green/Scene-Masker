package dev.eeasee.scenemasker.network.data.datas;

import com.google.common.collect.Sets;
import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.Operations;
import io.netty.buffer.Unpooled;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class MultiBlockOperationData implements BaseData {

    private Set<BlockPos> blockPosSet;
    private final Operations OPERATION;
    private final DataType DATA_TYPE = DataType.MULTI_BLOCK_OPERATION;

    public MultiBlockOperationData(Set<BlockPos> blockPosSet, Operations OPERATION) {
        this.blockPosSet = blockPosSet;
        this.OPERATION = OPERATION;
    }

    @Override
    public void apply() {

    }

    @Override
    public void encode(PacketByteBuf packetBuf) {
        packetBuf.writeEnumConstant(OPERATION);
        packetBuf.writeInt(blockPosSet.size());
        blockPosSet.forEach((packetBuf::writeBlockPos));
    }

    public static MultiBlockOperationData decode(PacketByteBuf packetByteBuf) {
        Operations dataHead = packetByteBuf.readEnumConstant(Operations.class);
        int size = packetByteBuf.readInt();
        Set<BlockPos> blockPosSet1 = Sets.newHashSet();
        for (int i = 0; i < size; i++) {
            blockPosSet1.add(
                    packetByteBuf.readBlockPos()
            );
        }
        return new MultiBlockOperationData(blockPosSet1, dataHead);
    }

}
