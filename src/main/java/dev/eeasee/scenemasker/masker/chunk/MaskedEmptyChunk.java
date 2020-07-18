package dev.eeasee.scenemasker.masker.chunk;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class MaskedEmptyChunk implements MaskedChunk {
    @Override
    public void setMaskBooleanState(BlockPos blockPos, boolean value) {
    }

    @Override
    public boolean getMaskBooleanState(BlockPos blockPos) {
        return false;
    }

    @Override
    public MaskedSection getSection(int index) {
        return null;
    }

    @Override
    public MaskedSection getOrCreateSection(int index) {
        return null;
    }

    @Override
    public boolean isSectionEmpty(int index) {
        return true;
    }

    @Override
    public ChunkPos getPos() {
        return null;
    }

    @Override
    public void clean() {
    }

    private static final MaskedEmptyChunk INSTANCE = new MaskedEmptyChunk();

    public static MaskedEmptyChunk getInstance() {
        return INSTANCE;
    }

    private MaskedEmptyChunk() {
    }
}
