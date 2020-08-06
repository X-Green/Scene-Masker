package dev.eeasee.scenemasker.network.data.s2c;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.utils.MaskProperties;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class SettingsData implements IDataS2C {

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
    public PacketByteBuf encode() {
        PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
        this.properties.flush(packetBuf);
        return packetBuf;
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        this.properties.read(packetByteBuf);
    }

}
