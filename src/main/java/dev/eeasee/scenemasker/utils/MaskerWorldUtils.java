package dev.eeasee.scenemasker.utils;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MaskerWorldUtils {

    public final static BlockState AIR_BLOCK_STATE = Blocks.AIR.getDefaultState();

    public static boolean isBlockRenderedMasked(BlockPos blockPos, MaskedWorld maskedWorld, MaskProperties properties) {
        if (!properties.isApplied) {
            return false;
        }
        if (properties.isLayered && blockPos.getY() != properties.appliedLayer) {
            return false;
        }
        boolean flag = maskedWorld.isBlockMasked(blockPos);
        return (properties.isReverted != flag);
    }

    public static boolean isBlockRenderedMasked(BlockPos blockPos, World world) {
        WorldInterface worldInterface = (WorldInterface)world;
        return isBlockRenderedMasked(blockPos, worldInterface.getWorldMasker(), worldInterface.getMaskProperties());
    }
}
