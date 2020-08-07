package dev.eeasee.scenemasker.fakes;

public interface ChunkSectionInterface {
    boolean getMaskerState(int x, int y, int z);

    void setMaskerState(int x, int y, int z, boolean value);

    boolean[] getMaskerStatesArrayCopied();

    void setMaskerStates(boolean[] booleans);

    void setMaskerStatesEmpty();

}
