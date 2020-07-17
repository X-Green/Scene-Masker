package dev.eeasee.scenemasker.network.data.datas;

import com.google.common.collect.Sets;
import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataHead;
import io.netty.buffer.Unpooled;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class MultiBlockPosData implements BaseData {

    private Set<BlockPos> blockPosSet;
    private DataHead dataHead;

    public MultiBlockPosData(Set<BlockPos> blockPosSet, DataHead dataHead) {
        this.blockPosSet = blockPosSet;
        this.dataHead = dataHead;
    }


    @Override
    public void apply() {

    }

    @Override
    public PacketByteBuf encode() {
        PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
        packetBuf.writeEnumConstant(dataHead);
        packetBuf.writeInt(blockPosSet.size());
        blockPosSet.forEach((packetBuf::writeBlockPos));
        return packetBuf;
    }

    public static MultiBlockPosData decode(PacketByteBuf packetByteBuf) {
        DataHead dataHead = packetByteBuf.readEnumConstant(DataHead.class);
        int size = packetByteBuf.readInt();
        Set<BlockPos> blockPosSet1 = Sets.newHashSet();
        for (int i = 0; i < size; i++) {
            blockPosSet1.add(
                    packetByteBuf.readBlockPos()
            );
        }
        return new MultiBlockPosData(blockPosSet1, dataHead);
    }

}
