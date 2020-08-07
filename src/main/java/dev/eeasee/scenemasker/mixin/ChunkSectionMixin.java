package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.ChunkSectionInterface;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkSection.class)
public abstract class ChunkSectionMixin implements ChunkSectionInterface {

    private boolean[] maskedBlocks = new boolean[4096];
    private int nonFalseMaskerCount = 0;
    private boolean isChanged = false;

    @Override
    public boolean getMaskerStateRaw(int x, int y, int z) {
        int index = z | y << 4 | x << 8;
        return maskedBlocks[index];
    }

    @Override
    public void setMaskerState(int x, int y, int z, boolean value) {
        int index = z | y << 4 | x << 8;
        if (value == true) {
            if (maskedBlocks == null) {
                maskedBlocks = new boolean[4096];
            } else {
                if (maskedBlocks[index] == false) {
                    maskedBlocks[index] = true;
                    nonFalseMaskerCount++;
                    isChanged = true;
                }
            }
        } else {
            if (maskedBlocks != null && maskedBlocks[index] == true) {
                maskedBlocks[index] = false;
                nonFalseMaskerCount--;
                isChanged = true;
            }
        }
    }

    @Override
    public boolean[] getMaskerStatesArrayCopied() {
        if (nonFalseMaskerCount == 0) {
            return null;
        } else {
            boolean[] booleans = new boolean[4096];
            System.arraycopy(maskedBlocks, 0, booleans, 0, 4096);
            return booleans;
        }
    }

    @Override
    public void setMaskerStates(boolean[] booleans) {
        this.nonFalseMaskerCount = countTrues(booleans);
        if (this.nonFalseMaskerCount == 0) {
            maskedBlocks = null;
        } else {
            if (maskedBlocks == null) {
                maskedBlocks = new boolean[4096];
            }
            System.arraycopy(booleans, 0, maskedBlocks, 0, 4096);
        }
        isChanged = true;
    }

    @Override
    public void setMaskerStatesEmpty() {
        this.nonFalseMaskerCount = 0;
        this.maskedBlocks = null;
    }

    @Override
    public boolean isChanged() {
        return this.isChanged;
    }

    @Override
    public void resetChanged() {
        this.isChanged = false;
    }

    private static int countTrues(boolean[] booleans) {
        if (booleans == null) {
            return 0;
        }
        int t = 0;
        for (int i = 0; i < 4096; i++) {
            if (booleans[i]) {
                t++;
            }
        }
        return t;
    }
}
