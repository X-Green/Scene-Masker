package dev.eeasee.scenemasker.masker;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class MaskedSection {
    private boolean[] booleans = new boolean[4096];
    public final static MaskedSection MASKED_EMPTY_SECTION = new MaskedEmptySection();

    public MaskedSection() {

    }

    public MaskedSection(Set<BlockPos> blockPosSet) {
        blockPosSet.forEach((blockPos -> {
            int x = blockPos.getX() & 15;
            int y = blockPos.getY() & 15;
            int z = blockPos.getZ() & 15;
            booleans[(x << 8) + (y << 4) + z] = true;
        }));
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

    public boolean getBoolean(BlockPos blockPos) {
        return getBoolean(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public boolean getBoolean(int x, int y, int z) {
        return getBooleanWithoutCheck(x & 15, y & 15, z & 15);
    }

    public boolean getBooleanWithoutCheck(int x, int y, int z) {
        return booleans[(x << 8) + (y << 4) + z];
    }

    public boolean isAllFalse() {
        boolean flag = false;
        for (int i = 0; i < 4096; i++) {
            flag |= booleans[i];
        }
        return !flag;
    }

    private static class MaskedEmptySection extends MaskedSection {
        MaskedEmptySection() {
        }

        public void setBoolean(BlockPos blockPos, boolean value) {
        }

        public void setBoolean(int x, int y, int z, boolean value) {
        }

        public void setBooleanWithoutCheck(int x, int y, int z, boolean value) {
        }

        public boolean getBoolean(BlockPos blockPos) {
            return false;
        }

        public boolean getBoolean(int x, int y, int z) {
            return false;
        }

        public boolean getBooleanWithoutCheck(int x, int y, int z) {
            return false;
        }

        public boolean isAllFalse() {
            return true;
        }


    }
}
