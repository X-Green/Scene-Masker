package dev.eeasee.scenemasker.network.data.s2c;

import dev.eeasee.scenemasker.network.data.IData;
import dev.eeasee.scenemasker.network.data.DataType;
import dev.eeasee.scenemasker.network.data.PacketSide;
import net.minecraft.util.PacketByteBuf;

public class SettingsData implements IData {

    @Override
    public void apply() {

    }

    @Override
    public void encode(PacketByteBuf packetBuf) {
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {

    }

    @Override
    public DataType getDataType() {
        return DataType.SETTINGS;
    }

    @Override
    public PacketSide getSide() {
        return PacketSide.SERVER_TO_CLIENT;
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
