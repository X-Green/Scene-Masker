package dev.eeasee.scenemasker.masker.chunk;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public interface IMaskedChunk {
    void setMaskBooleanState(BlockPos blockPos, boolean value);
    boolean getMaskBooleanState(BlockPos blockPos);
    MaskedSection getSection(int index);
    boolean isSectionEmpty(int index);
    ChunkPos getPos();
    void clean();
}
