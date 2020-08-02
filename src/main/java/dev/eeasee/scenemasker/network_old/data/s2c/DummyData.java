package dev.eeasee.scenemasker.network_old.data.s2c;

import dev.eeasee.scenemasker.network_old.data.IData;
import dev.eeasee.scenemasker.network_old.data.DataType;
import dev.eeasee.scenemasker.network_old.data.PacketSide;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class DummyData implements IData {

    @Override
    public void apply(ClientPlayerEntity playerEntity) {

    }

    @Override
    public void encode(PacketByteBuf packetByteBuf) {

    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {

    }

    @Override
    public DataType getDataType() {
        return null;
    }

    @Override
    public PacketSide getSide() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
