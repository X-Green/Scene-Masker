package dev.eeasee.scenemasker.utils;

import net.minecraft.util.PacketByteBuf;

public class MaskProperties {
    public boolean isApplied = false;
    public boolean isLayered = false;
    public boolean isReverted = false;

    public int appliedLayer = 0;

    public void flush(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBoolean(isApplied);
        packetByteBuf.writeBoolean(isLayered);
        packetByteBuf.writeBoolean(isReverted);

        packetByteBuf.writeInt(appliedLayer);
    }

    public void read(PacketByteBuf packetByteBuf) {
        this.isApplied = packetByteBuf.readBoolean();
        this.isLayered = packetByteBuf.readBoolean();
        this.isReverted = packetByteBuf.readBoolean();

        this.appliedLayer = packetByteBuf.readInt();
    }

    public void copy(MaskProperties properties) {
        this.isApplied = properties.isApplied;
        this.isLayered = properties.isLayered;
        this.isReverted = properties.isReverted;

        this.appliedLayer = properties.appliedLayer;
    }
}
