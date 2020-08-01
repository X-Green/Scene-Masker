package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.ServerWorldInterface;
import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.network.CustomPayloadFactory;
import dev.eeasee.scenemasker.network.data.IData;
import dev.eeasee.scenemasker.network.data.s2c.ChunkSectionUpdateData;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

import java.util.List;
import java.util.function.BiFunction;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements ServerWorldInterface {
    protected ServerWorldMixin(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> chunkManagerProvider, Profiler profiler, boolean isClient) {
        super(levelProperties, dimensionType, chunkManagerProvider, profiler, isClient);
    }

    @Final
    @Shadow
    private List<ServerPlayerEntity> players;

    @Override
    public void sendMaskedWorldChangeToAllPlayers() {
        MaskedWorld worldMasker = ((WorldInterface)this).getWorldMasker();
        worldMasker.flushChunkChangeSet(chunkPos -> {
            worldMasker.flushChunkChangeSet(chunkPos1 -> {
                for (int i = 0; i < 4; i++) {
                    CustomPayloadS2CPacket payloadS2CPacket = CustomPayloadFactory.create(worldMasker.createChunkSectionUpdateData(ChunkSectionPos.from(chunkPos, i)));
                    for (ServerPlayerEntity player : this.players) {
                        player.networkHandler.sendPacket(payloadS2CPacket);
                    }
                }
            });
        });
    }

}
