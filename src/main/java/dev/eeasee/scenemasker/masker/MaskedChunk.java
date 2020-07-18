package dev.eeasee.scenemasker.masker;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class MaskedChunk {
    private final ChunkPos chunkPos;
    private MaskedSection[] sections = new MaskedSection[4];
    public final static MaskedChunk EMPTY = new EmptyMaskedChunk();
    private boolean changed = false;

    public MaskedChunk(ChunkPos chunkPos) {
        this.chunkPos = chunkPos;
    }

    public void setMaskBooleanState(BlockPos blockPos, boolean value) {
        int sectionIndex = blockPos.getY() >> 4;
        if ((value) || !this.isSectionEmpty(sectionIndex)) {
            this.getOrCreateSection(sectionIndex).setBoolean(blockPos, value);
        }
        changed = true;
    }

    public boolean getMaskBooleanState(BlockPos blockPos) {
        int index = blockPos.getY() >> 4;
        if (this.isSectionEmpty(index)) {
            return false;
        } else {
            return this.getSectionOrEmpty(index).getBoolean(blockPos);
        }
    }

    public MaskedSection getSectionOrEmpty(int index) {
        MaskedSection target = sections[index];
        return (target == null) ? MaskedSection.MASKED_EMPTY_SECTION : target;
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

    public boolean isEmptyChunk() {
        return false;
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


    private static class EmptyMaskedChunk extends MaskedChunk {

        public EmptyMaskedChunk() {
            super(null);
        }

        public void setMaskBooleanState(BlockPos blockPos, boolean value) {
        }

        public boolean getMaskBooleanState(BlockPos blockPos) {
            return false;
        }

        public MaskedSection getSection(int index) {
            return MaskedSection.MASKED_EMPTY_SECTION;
        }

        public MaskedSection getOrCreateSection(int index) {
            return MaskedSection.MASKED_EMPTY_SECTION;
        }

        public boolean isSectionEmpty(int index) {
            return true;
        }

        public boolean isEmptyChunk() {
            return true;
        }

        public ChunkPos getPos() {
            return null;
        }

        public void clean() {
        }

    }
}
