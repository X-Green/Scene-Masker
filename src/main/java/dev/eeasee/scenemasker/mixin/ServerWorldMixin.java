package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.ServerWorldInterface;
import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.network_old.CustomPayloadFactory;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;
import java.util.function.BiFunction;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements ServerWorldInterface {
    protected ServerWorldMixin(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> chunkManagerProvider, Profiler profiler, boolean isClient) {
        super(levelProperties, dimensionType, chunkManagerProvider, profiler, isClient);
    }

    @Shadow
    @Final
    private MinecraftServer server;

    @Override
    public void sendMaskedWorldChangeToAllPlayers() {
        MaskedWorld worldMasker = ((WorldInterface) this).getWorldMasker();
        Set<ChunkPos> chunkPosSet = worldMasker.getChangeSet();
        boolean isRemote = server.isDedicated();
        for (ChunkPos chunkPos : chunkPosSet) {
            for (int i = 0; i < 16; i++) {
                CustomPayloadS2CPacket payloadS2CPacket = CustomPayloadFactory.create(worldMasker.createChunkSectionUpdateData(ChunkSectionPos.from(chunkPos, i)));
                if (isRemote) {
                    this.server.getPlayerManager().sendToAll(payloadS2CPacket);
                } else {
                    if (payloadS2CPacket != null) {
                        CustomPayloadFactory.handle(payloadS2CPacket.getData(), MinecraftClient.getInstance().player);
                    } else {
                        System.out.println("NOOOOOOOOOOOOOOOOOOOOOo");
                    }
                }
            }
        }
    }

}
