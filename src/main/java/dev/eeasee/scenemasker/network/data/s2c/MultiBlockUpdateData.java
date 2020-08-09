package dev.eeasee.scenemasker.network.data.s2c;

import dev.eeasee.scenemasker.utils.MaskerWorldUtils;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiBlockUpdateData implements IDataS2C {

    private BlockPos[] stateTruePoses;

    private BlockPos[] stateFalsePoses;

    public MultiBlockUpdateData(BlockPos[] stateTruePoses, BlockPos[] stateFalsePoses) {
        this.stateTruePoses = stateTruePoses;
        this.stateFalsePoses = stateFalsePoses;
    }

    public MultiBlockUpdateData() {

    }

    @Override
    public void apply(ClientPlayerEntity clientPlayerEntity) {
        World world = clientPlayerEntity.clientWorld;
        if (stateTruePoses != null) {
            for (BlockPos posTrue : stateTruePoses) {
                MaskerWorldUtils.setBlockMaskerState(world, posTrue, true);
            }
        }
        if (stateFalsePoses != null) {
            for (BlockPos posFalse : stateFalsePoses) {
                MaskerWorldUtils.setBlockMaskerState(world, posFalse, false);
            }
        }
    }

    @Override
    public PacketByteBuf encode() {
        PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
        if (stateTruePoses != null && stateTruePoses.length != 0) {
            packetBuf.writeBoolean(true);
            packetBuf.writeBoolean(true);
            packetBuf.writeInt(stateTruePoses.length);
            for (BlockPos posTrue : stateTruePoses) {
                packetBuf.writeBlockPos(posTrue);
            }
        }
        if (stateFalsePoses != null && stateFalsePoses.length != 0) {
            packetBuf.writeBoolean(true);
            packetBuf.writeBoolean(false);
            packetBuf.writeInt(stateFalsePoses.length);
            for (BlockPos posFalse : stateFalsePoses) {
                packetBuf.writeBlockPos(posFalse);
            }
        }
        packetBuf.writeBoolean(false);
        return packetBuf;
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        while (packetByteBuf.readBoolean()) {
            boolean posState = packetByteBuf.readBoolean();
            int length = packetByteBuf.readInt();
            BlockPos[] poses = new BlockPos[length];
            for (int i = 0; i < length; i++) {
                poses[i] = packetByteBuf.readBlockPos();
            }
            if (posState) {
                this.stateTruePoses = poses;
            } else {
                this.stateFalsePoses = poses;
            }
        }
    }
}
