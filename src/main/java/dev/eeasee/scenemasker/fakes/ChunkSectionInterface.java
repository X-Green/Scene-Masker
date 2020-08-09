package dev.eeasee.scenemasker.fakes;

import net.minecraft.util.math.BlockPos;

public interface ChunkSectionInterface {
    boolean getMaskerStateRawXYZ(int x, int y, int z);

    boolean getMaskerState(int x, int y, int z);

    boolean getMaskerState(BlockPos blockPos);

    void setMaskerStateRawXYZ(int x, int y, int z, boolean value, boolean check);

    void setMaskerState(int x, int y, int z, boolean value, boolean check);

    void setMaskerState(int x, int y, int z, boolean value);

    void doCheck();

    boolean[] getMaskerStatesArrayCopied();

    void setMaskerStates(boolean[] booleans);

    void setMaskerStatesEmpty();

    boolean isMaskerChanged();

    void resetMaskerChanged();

}
