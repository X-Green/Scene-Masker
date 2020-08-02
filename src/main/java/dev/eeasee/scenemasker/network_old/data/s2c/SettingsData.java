package dev.eeasee.scenemasker.network_old.data.s2c;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.network_old.data.IData;
import dev.eeasee.scenemasker.network_old.data.DataType;
import dev.eeasee.scenemasker.network_old.data.PacketSide;
import dev.eeasee.scenemasker.utils.MaskProperties;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class SettingsData implements IData {

    private final MaskProperties properties;

    public SettingsData(MaskProperties properties) {
        this.properties = properties;
    }

    public SettingsData() {
        this.properties = new MaskProperties();
    }

    @Override
    public void apply(ClientPlayerEntity clientPlayerEntity) {
        ((WorldInterface)clientPlayerEntity.clientWorld).getMaskProperties().copy(this.properties);
    }

    @Override
    public void encode(PacketByteBuf packetBuf) {
        this.properties.flush(packetBuf);
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        this.properties.read(packetByteBuf);
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
        return true;
    }

}
