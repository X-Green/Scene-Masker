package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.ClientWorldInterface;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.BiFunction;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World implements ClientWorldInterface {
    protected ClientWorldMixin(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> chunkManagerProvider, Profiler profiler, boolean isClient) {
        super(levelProperties, dimensionType, chunkManagerProvider, profiler, isClient);
        this.worldMasker = new MaskedWorld();
    }

    private final MaskedWorld worldMasker;

    @Override
    public MaskedWorld getWorldMasker() {
        return this.worldMasker;
    }
}
