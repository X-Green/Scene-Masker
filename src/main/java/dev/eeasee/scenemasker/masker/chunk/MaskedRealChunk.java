package dev.eeasee.scenemasker.masker.chunk;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class MaskedRealChunk {
    private final ChunkPos chunkPos;
    private MaskedSection[] sections = new MaskedSection[4];
    private boolean changed = false;

    public MaskedRealChunk(ChunkPos chunkPos) {
        this.chunkPos = chunkPos;
    }

    public void setMaskBooleanState(BlockPos blockPos, boolean value) {
        int sectionIndex = blockPos.getY() >> 4;
        if ((value) || ! this.isSectionEmpty(sectionIndex)) {
            this.getSection(sectionIndex).setBoolean(blockPos, value);
        }
        changed = true;
    }

    public boolean getMaskBooleanState(BlockPos blockPos) {
        int index = blockPos.getY() >> 4;
        if (isSectionEmpty(index)) {

        }

        return false;
    }

    public MaskedSection getSection(int index) {
        return sections[index];
    }

    public MaskedSection getOrCreateSection(int index) {
        MaskedSection target = sections[index];
        if (target == null) {
            target = new MaskedSection();
            sections[index] = target;
        }
        return target;
    }

    public boolean isSectionEmpty(int index) {
        return sections[index] == null;
    }

    public ChunkPos getPos() {
        return this.chunkPos;
    }

    public void clean() {
        if (!changed) return;
        for (int i = 0; i < 4; i++) {
            if (sections[i].isAllFalse()) {
                sections[i] = null;
            }
        }
    }
}
