package dev.eeasee.scenemasker.network.packet.s2c;

import com.google.common.collect.Lists;
import dev.eeasee.scenemasker.client.MaskConfigs;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PacketByteBuf;

import java.util.List;

public class ConfigsS2C implements IDataS2C {

    private final List<MaskerConfigEntry> entries;

    public ConfigsS2C() {
        entries = Lists.newArrayList();
    }

    public ConfigsS2C(MaskerConfigEntry... configEntries) {
        entries = Lists.newArrayList(configEntries);
    }

    public ConfigsS2C(String name, String value) {
        entries = Lists.newArrayList(new MaskerConfigEntry(name, value));
    }

    @Override
    public void apply(ClientPlayerEntity clientPlayerEntity) {
        entries.forEach(maskerConfigEntry -> {
            String name = maskerConfigEntry.name;
            String value = maskerConfigEntry.value;
            switch (name) {
                case "isLayered":
                    MaskConfigs.isLayered = Boolean.getBoolean(value);
                case "isReverted":
                    MaskConfigs.isReverted = Boolean.getBoolean(value);
                case "appliedLayer":
                    MaskConfigs.appliedLayer = Integer.getInteger(value);
                case "opacity":
                    MaskConfigs.opacity = Float.parseFloat(value);
            }
        });
    }

    @Override
    public PacketByteBuf encode() {
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        entries.forEach(maskerConfigEntry -> {
            packetByteBuf.writeBoolean(true);
            packetByteBuf.writeString(maskerConfigEntry.name);
            packetByteBuf.writeString(maskerConfigEntry.value);
        });
        packetByteBuf.writeBoolean(false);
        return packetByteBuf;
    }

    @Override
    public void decode(PacketByteBuf packetByteBuf) {
        while (packetByteBuf.readBoolean()) {
            entries.add(new MaskerConfigEntry(packetByteBuf.readString(), packetByteBuf.readString()));
        }
    }

    public static class MaskerConfigEntry {
        String name;
        String value;

        public MaskerConfigEntry(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
