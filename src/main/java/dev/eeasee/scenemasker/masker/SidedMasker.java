package dev.eeasee.scenemasker.masker;

import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.util.math.BlockPos;

public class SidedMasker {
    private MaskerProperties properties = new MaskerProperties();
    private MaskedWorld maskedWorld;
    public final Side SIDE;

    public SidedMasker(Side side) {
        this.SIDE = side;
    }

    public boolean isBlockRenderedMasked(BlockPos blockPos) {
        if (!properties.isApplied) {
            return false;
        }
        if (properties.isLayered && blockPos.getY() != properties.appliedLayer) {
            return false;
        }
        boolean flag = maskedWorld.isBlockMasked(blockPos);
        return (properties.isReverted != flag);
    }

    public enum Side {
        SERVER,
        CLIENT
    }

    public static class MaskerProperties {
        public boolean isApplied = true;
        public boolean isLayered = false;
        public boolean isReverted = false;

        public int appliedLayer = 0;
    }

}
