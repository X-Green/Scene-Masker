package dev.eeasee.scenemasker.world;

import dev.eeasee.scenemasker.Masker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class MaskedSection {
    private boolean[] booleans = new boolean[4096];
    public final static MaskedSection MASKED_EMPTY_SECTION = new MaskedEmptySection();
    private final ChunkSectionPos sectionPos;

    public MaskedSection(ChunkSectionPos sectionPos) {
        this.sectionPos = sectionPos;
    }

    public MaskedSection(Set<BlockPos> blockPosSet, ChunkSectionPos sectionPos) {
        blockPosSet.forEach((blockPos -> {
            int x = blockPos.getX() & 15;
            int y = blockPos.getY() & 15;
            int z = blockPos.getZ() & 15;
            booleans[(x << 8) + (y << 4) + z] = true;
        }));
        this.sectionPos = sectionPos;
    }

    public void setBoolean(BlockPos blockPos, boolean value) {
        setBoolean(blockPos.getX(), blockPos.getY(), blockPos.getZ(), value);
    }

    public void setBoolean(int x, int y, int z, boolean value) {
        setBooleanWithoutCheck(x & 15, y & 15, z & 15, value);
    }

    public void setBooleanWithoutCheck(int x, int y, int z, boolean value) {
        booleans[(x << 8) + (y << 4) + z] = value;
    }

    public void setBooleanWithoutCheck(int index, boolean value) {
        booleans[index] = value;
    }

    public void setBooleansArray(boolean[] booleans) {
        if (booleans.length != 4096) {
            Masker.LOGGER.error("Unsupported section data for masker!");
        } else {
            System.arraycopy(booleans, 0, this.booleans, 0, 4096);
        }
    }

    public boolean getBoolean(BlockPos blockPos) {
        return getBoolean(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public boolean getBoolean(int x, int y, int z) {
        return getBooleanWithoutCheck(x & 15, y & 15, z & 15);
    }

    public boolean getBooleanWithoutCheck(int x, int y, int z) {
        return booleans[(x << 8) + (y << 4) + z];
    }

    public boolean getBooleanWithoutCheck(int index) {
        return booleans[index];
    }

    public boolean[] getBooleansArrayCopied() {
        boolean[] target = new boolean[4096];
        System.arraycopy(this.booleans, 0, target, 0, 4096);
        return target;
    }

    public boolean isAllFalse() {
        boolean flag = false;
        for (int i = 0; i < 4096; i++) {
            flag |= booleans[i];
        }
        return !flag;
    }

    public ChunkSectionPos getSectionPos() {
        return this.sectionPos;
    }

    private static class MaskedEmptySection extends MaskedSection {
        MaskedEmptySection() {
            super(null);
        }

        @Override
        public void setBoolean(BlockPos blockPos, boolean value) {
        }

        @Override
        public void setBoolean(int x, int y, int z, boolean value) {
        }

        @Override
        public void setBooleanWithoutCheck(int x, int y, int z, boolean value) {
        }

        @Override
        public void setBooleanWithoutCheck(int index, boolean value) {
        }

        @Override
        public void setBooleansArray(boolean[] booleans) {
        }

        @Override
        public boolean getBoolean(BlockPos blockPos) {
            return false;
        }

        @Override
        public boolean getBoolean(int x, int y, int z) {
            return false;
        }

        @Override
        public boolean getBooleanWithoutCheck(int x, int y, int z) {
            return false;
        }

        @Override
        public boolean getBooleanWithoutCheck(int index) {
            return false;
        }

        @Override
        public boolean[] getBooleansArrayCopied() {
            return new boolean[4096];
        }

        @Override
        public boolean isAllFalse() {
            return true;
        }

        @Override
        public ChunkSectionPos getSectionPos() {
            return null;
        }

    }
}
