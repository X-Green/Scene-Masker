package dev.eeasee.scenemasker.network.data.s2c;

import com.google.common.collect.Sets;
import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.world.MaskedWorld;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class MultiBlockUpdateData implements IDataS2C {

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
    public PacketByteBuf encode() {
        PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
        packetBuf.writeBoolean(value);
        packetBuf.writeInt(blockPosSet.size());
        blockPosSet.forEach((packetBuf::writeBlockPos));
        return packetBuf;
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

}
