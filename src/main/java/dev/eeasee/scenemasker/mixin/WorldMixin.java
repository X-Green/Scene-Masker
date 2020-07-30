package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;

@Mixin(World.class)
public abstract class WorldMixin implements IWorld, WorldInterface {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initWorldMasker(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> chunkManagerProvider, Profiler profiler, boolean isClient, CallbackInfo ci) {
        this.worldMasker = new MaskedWorld();
    }

    private MaskedWorld worldMasker;

    @Override
    public MaskedWorld getWorldMasker() {
        return this.worldMasker;
    }
}
