package dev.eeasee.scenemasker.network.data.datas;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import net.minecraft.util.PacketByteBuf;

public class SettingsData implements BaseData {


    private final DataType DATA_TYPE = DataType.MULTI_BLOCK_UPDATE;

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
    public boolean isValid() {
        return false;
    }

}
