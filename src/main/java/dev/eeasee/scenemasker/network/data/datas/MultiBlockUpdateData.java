package dev.eeasee.scenemasker.network.data.datas;

import com.google.common.collect.Sets;
import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.PacketSide;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class MultiBlockUpdateData implements BaseData {

    private boolean value;

    private Set<BlockPos> blockPosSet;

    private final PacketSide SIDE;


    public MultiBlockUpdateData(Set<BlockPos> blockPosSet, boolean value, PacketSide side) {
        this.value = value;
        this.blockPosSet = blockPosSet;
        this.SIDE = side;
    }

    public MultiBlockUpdateData(PacketSide side) {
        this.SIDE = side;
    }

    @Override
    public void apply() {
    }

    @Override
    public void encode(PacketByteBuf packetBuf) {
        packetBuf.writeBoolean(value);
        packetBuf.writeInt(blockPosSet.size());
        blockPosSet.forEach((packetBuf::writeBlockPos));
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        boolean value = packetByteBuf.readBoolean();
        int size = packetByteBuf.readInt();
        Set<BlockPos> blockPosSet1 = Sets.newHashSet();
        for (int i = 0; i < size; i++) {
            blockPosSet1.add(
                    packetByteBuf.readBlockPos()
            );
        }

        this.value = value;
        this.blockPosSet = blockPosSet1;
    }

    @Override
    public DataType getDataType() {
        return DataType.MULTI_BLOCK_UPDATE;
    }

    @Override
    public PacketSide getSide() {
        return SIDE;
    }

    @Override
    public boolean isValid() {
        return this.blockPosSet != null;
    }

}
