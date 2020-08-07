package dev.eeasee.scenemasker.fakes;

import net.minecraft.util.math.BlockPos;

public interface ChunkSectionInterface {
    boolean getMaskerStateRaw(int x, int y, int z);

    boolean getMaskerState(int x, int y, int z);

    boolean getMaskerState(BlockPos blockPos);

    void setMaskerState(int x, int y, int z, boolean value);

    boolean[] getMaskerStatesArrayCopied();

    void setMaskerStates(boolean[] booleans);

    void setMaskerStatesEmpty();

    boolean isChanged();

    void resetChanged();

}
