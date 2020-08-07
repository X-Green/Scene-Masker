package dev.eeasee.scenemasker.network.data.s2c;

import dev.eeasee.scenemasker.client.MaskProperties;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class SettingsData implements IDataS2C {

    private String name;
    private String value;

    public SettingsData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void apply(ClientPlayerEntity clientPlayerEntity) {
        switch (name) {
            case "isApplied":
                MaskProperties.isApplied = Boolean.getBoolean(value);
            case "isLayered":
                MaskProperties.isLayered = Boolean.getBoolean(value);
            case "isReverted":
                MaskProperties.isReverted = Boolean.getBoolean(value);
            case "appliedLayer":
                MaskProperties.appliedLayer = Integer.getInteger(value);
        }
    }

    @Override
    public PacketByteBuf encode() {
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        packetByteBuf.writeString(name);
        packetByteBuf.writeString(value);
        return packetByteBuf;
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        this.name = packetByteBuf.readString();
        this.value = packetByteBuf.readString();
    }

}
