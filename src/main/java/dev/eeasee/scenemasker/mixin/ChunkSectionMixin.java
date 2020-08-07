package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.ChunkSectionInterface;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkSection.class)
public abstract class ChunkSectionMixin implements ChunkSectionInterface {

    private boolean[] maskedBlocks = new boolean[4096];

    @Override
    public boolean getMaskerState(int x, int y, int z) {
        int index = z | y << 4 | x << 8;
        return maskedBlocks[index];
    }

    @Override
    public void setMaskerState(int x, int y, int z, boolean value) {
        int index = z | y << 4 | x << 8;
        maskedBlocks[index] = value;
    }

    @Override
    public boolean[] getMaskerStatesArrayCopied() {
        boolean[] booleans = new boolean[4096];
        System.arraycopy(maskedBlocks, 0, booleans, 0, 4096);
        return booleans;
    }

    @Override
    public void setMaskerStates(boolean[] booleans) {
        System.arraycopy(booleans, 0, maskedBlocks, 0, 4096);
    }
}
