package dev.eeasee.scenemasker.utils;

import dev.eeasee.scenemasker.world.MaskedWorld;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.util.math.BlockPos;

public class MaskerWorldUtils {

    public boolean isBlockRenderedMasked(BlockPos blockPos, MaskedWorld maskedWorld, MaskProperties properties) {
        if (!properties.isApplied) {
            return false;
        }
        if (properties.isLayered && blockPos.getY() != properties.appliedLayer) {
            return false;
        }
        boolean flag = maskedWorld.isBlockMasked(blockPos);
        return (properties.isReverted != flag);
    }

}
