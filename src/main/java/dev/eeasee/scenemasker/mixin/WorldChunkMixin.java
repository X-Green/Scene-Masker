package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.chunk.MaskedChunk;
import dev.eeasee.scenemasker.fakes.WorldChunkInterface;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldChunk.class)
public abstract class WorldChunkMixin implements WorldChunkInterface {

    private final MaskedChunk maskedChunk = new MaskedChunk();

    @Override
    public MaskedChunk getMaskedChunk() {
        return this.maskedChunk;
    }
}
