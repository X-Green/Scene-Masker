package dev.eeasee.scenemasker.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;

public class MaskedChunk {
    private final ChunkPos chunkPos;
    private MaskedSection[] sections = new MaskedSection[16];
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
            target = new MaskedSection(ChunkSectionPos.from(this.chunkPos.x, index, this.chunkPos.z));
            sections[index] = target;
        }
        return target;
    }

    public void setSection(boolean[] values, int index) {
        MaskedSection section = new MaskedSection(ChunkSectionPos.from(this.chunkPos.x, index, this.chunkPos.z));
        section.setBooleansArray(values);
        sections[index] = section;
    }

    public boolean isSectionEmpty(int index) {
        return sections[index] == null;
    }

    public boolean isEmptyChunk() {
        for (int i = 0; i < 16; i++) {
            MaskedSection section = sections[i];
            if (section != null && (!section.isAllFalse())) {
                return false;
            }
        }
        return true;
    }

    public ChunkPos getPos() {
        return this.chunkPos;
    }

    public void clean() {
        if (!changed) return;
        for (int i = 0; i < 16; i++) {
            if (sections[i].isAllFalse()) {
                sections[i] = null;
            }
        }
    }


    private static class EmptyMaskedChunk extends MaskedChunk {

        public EmptyMaskedChunk() {
            super(null);
        }

        @Override
        public void setMaskBooleanState(BlockPos blockPos, boolean value) {
        }

        @Override
        public boolean getMaskBooleanState(BlockPos blockPos) {
            return false;
        }

        @Override
        public MaskedSection getSectionOrEmpty(int index) {
            return MaskedSection.MASKED_EMPTY_SECTION;
        }

        @Override
        public MaskedSection getOrCreateSection(int index) {
            return MaskedSection.MASKED_EMPTY_SECTION;
        }

        @Override
        public void setSection(boolean[] values, int index) {
        }

        @Override
        public boolean isSectionEmpty(int index) {
            return true;
        }

        @Override
        public boolean isEmptyChunk() {
            return true;
        }

        @Override
        public ChunkPos getPos() {
            return null;
        }

        @Override
        public void clean() {
        }

    }
}
