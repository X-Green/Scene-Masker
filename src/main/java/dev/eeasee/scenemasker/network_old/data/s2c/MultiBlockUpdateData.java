package dev.eeasee.scenemasker.network_old.data.s2c;

import com.google.common.collect.Sets;
import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.network_old.data.IData;
import dev.eeasee.scenemasker.network_old.data.DataType;
import dev.eeasee.scenemasker.network_old.data.PacketSide;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class MultiBlockUpdateData implements IData {

    private boolean value;

    private Set<BlockPos> blockPosSet;

    public MultiBlockUpdateData(Set<BlockPos> blockPosSet, boolean value) {
        this.value = value;
        this.blockPosSet = blockPosSet;
    }

    public MultiBlockUpdateData() {

    }

    @Override
    public void apply(ClientPlayerEntity clientPlayerEntity) {
        ClientWorld world = clientPlayerEntity.clientWorld;
        MaskedWorld maskedWorld = ((WorldInterface)world).getWorldMasker();
        this.blockPosSet.forEach(blockPos -> {
            maskedWorld.setBlockMasked(blockPos, value);
        });
        maskedWorld.cleanEmptyChunks();
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
        return PacketSide.SERVER_TO_CLIENT;
    }

    @Override
    public boolean isValid() {
        return this.blockPosSet != null;
    }

}
