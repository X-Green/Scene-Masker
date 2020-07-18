package dev.eeasee.scenemasker.masker;

import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.util.math.BlockPos;

public class SidedMasker {
    private MaskerProperties properties = new MaskerProperties();
    private MaskedWorld maskedWorld;


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

    public static enum Side {
        SERVER,
        CLIENT
    }
}
