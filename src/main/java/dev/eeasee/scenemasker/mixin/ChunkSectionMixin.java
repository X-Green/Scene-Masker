package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.ChunkSectionInterface;
import dev.eeasee.scenemasker.utils.BooleanUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkSection.class)
public class ChunkSectionMixin implements ChunkSectionInterface {

    private boolean[] maskedBlocks = new boolean[4096];
    private boolean isMaskerChanged = false;

    private boolean getMaskerStateRawIndex(int index) {
        return (maskedBlocks == null) ? false : maskedBlocks[index];
    }

    private void setMaskerStateRawIndex(int index, boolean value, boolean check) {
        if (check && (value == false) && (maskedBlocks == null)) {
            return;
        } else {
            if (maskedBlocks == null) {
                maskedBlocks = new boolean[4096];
                isMaskerChanged |= value;
            } else {
                isMaskerChanged |= (maskedBlocks[index] == value);
            }
            maskedBlocks[index] = value;
        }
        if (check && (!BooleanUtils.or(maskedBlocks))) {
            maskedBlocks = null;
        }
    }

    @Override
    public boolean getMaskerStateRawXYZ(int x, int y, int z) {
        int index = z | y << 4 | x << 8;
        return getMaskerStateRawIndex(index);
    }

    @Override
    public boolean getMaskerState(int x, int y, int z) {
        int index = (z & 15) | (y & 15) << 4 | (x & 15) << 8;
        return getMaskerStateRawIndex(index);
    }

    @Override
    public boolean getMaskerState(BlockPos blockPos) {
        return getMaskerState(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public void setMaskerStateRawXYZ(int x, int y, int z, boolean value, boolean check) {
        setMaskerStateRawIndex((z) | (y << 4) | (x << 8), value, check);
    }

    @Override
    public void setMaskerState(int x, int y, int z, boolean value, boolean check) {
        setMaskerStateRawIndex((z & 15) | (y & 15) << 4 | (x & 15) << 8, value, check);
    }

    @Override
    public void setMaskerState(int x, int y, int z, boolean value) {
        setMaskerState(x, y, z, value, true);
    }

    @Override
    public void doCheck() {
        if (maskedBlocks != null) {
            if (!BooleanUtils.or(maskedBlocks)) {
                maskedBlocks = null;
            }
        }
    }

    @Override
    public boolean[] getMaskerStatesArrayCopied() {
        if (!BooleanUtils.or(this.maskedBlocks)) {
            return null;
        } else {
            boolean[] booleans = new boolean[4096];
            System.arraycopy(maskedBlocks, 0, booleans, 0, 4096);
            return booleans;
        }
    }

    @Override
    public void setMaskerStates(boolean[] booleans) {
        if (booleans == null || !BooleanUtils.or(booleans)) {
            maskedBlocks = null;
        } else {
            if (maskedBlocks == null) {
                maskedBlocks = new boolean[4096];
            }
            System.arraycopy(booleans, 0, maskedBlocks, 0, 4096);
        }
        isMaskerChanged = true;
    }

    @Override
    public void setMaskerStatesEmpty() {
        this.maskedBlocks = null;
    }

    @Override
    public boolean isMaskerChanged() {
        return this.isMaskerChanged;
    }

    @Override
    public void resetMaskerChanged() {
        this.isMaskerChanged = false;
    }

}
