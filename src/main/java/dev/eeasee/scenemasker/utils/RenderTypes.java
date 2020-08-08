package dev.eeasee.scenemasker.utils;

public enum RenderTypes {
    NORMAL,
    APPLIED,
    REVERTED;

    public Enum<RenderTypes> cycle(boolean forward) {
        int id = this.ordinal();
        if (forward) {
            if (++id >= values().length) {
                id = 0;
            }
        } else {
            if (--id < 0) {
                id = values().length - 1;
            }
        }
        return values()[id % values().length];
    }
}
