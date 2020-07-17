package dev.eeasee.scenemasker.masker.chunk;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class MaskedChunk implements IMaskedChunk {
    private final ChunkPos chunkPos;
    private MaskedSection[] subChunks = new MaskedSection[4];
    private boolean changed = false;

    public MaskedChunk(ChunkPos chunkPos) {
        this.chunkPos = chunkPos;
    }

    @Override
    public void setMaskBooleanState(BlockPos blockPos, boolean value) {
        int sectionIndex = blockPos.getY() >> 4;
        if ((value) || ! this.isSectionEmpty(sectionIndex)) {
            this.getSection(sectionIndex).setBoolean(blockPos, value);
        }
        changed = true;
    }

    @Override
    public boolean getMaskBooleanState(BlockPos blockPos) {
        return false;
    }

    @Override
    public MaskedSection getSection(int index) {
        MaskedSection target = subChunks[index];
        if (target == null) {
            target = new MaskedSection();
            subChunks[index] = target;
        }
        return target;
    }

    @Override
    public boolean isSectionEmpty(int index) {
        return subChunks[index] == null;
    }

    @Override
    public ChunkPos getPos() {
        return this.chunkPos;
    }

    @Override
    public void clean() {
        if (!changed) return;
        for (int i = 0; i < 4; i++) {
            if (subChunks[i].isAllFalse()) {
                subChunks[i] = null;
            }
        }
    }
}
