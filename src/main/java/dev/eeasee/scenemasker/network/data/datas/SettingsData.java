package dev.eeasee.scenemasker.network.data.datas;

import dev.eeasee.scenemasker.network.data.BaseData;
import dev.eeasee.scenemasker.network.data.DataType;
import net.minecraft.util.PacketByteBuf;

public class SettingsData implements BaseData {


    private final DataType DATA_TYPE = DataType.MULTI_BLOCK_OPERATION;

    @Override
    public void apply() {

    }

    @Override
    public void encode(PacketByteBuf packetBuf) {
    }

    public static SettingsData decode(PacketByteBuf packetBuf) {
        return new SettingsData();
    }

}
