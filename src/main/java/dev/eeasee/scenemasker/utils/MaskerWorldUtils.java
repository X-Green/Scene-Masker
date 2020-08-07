package dev.eeasee.scenemasker.utils;

import dev.eeasee.scenemasker.client.MaskProperties;
import dev.eeasee.scenemasker.fakes.ChunkSectionInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

public class MaskerWorldUtils {

    public final static BlockState AIR_BLOCK_STATE = Blocks.AIR.getDefaultState();

    public static boolean shouldBlockRender(BlockPos blockPos, WorldChunk chunk) {
        if (!MaskProperties.isApplied) {
            return false;
        }
        if (MaskProperties.isLayered && blockPos.getY() != MaskProperties.appliedLayer) {
            return false;
        }
        boolean flag = ((ChunkSectionInterface)chunk.getSectionArray()[blockPos.getY() << 4]).getMaskerState(blockPos);
        return (MaskProperties.isReverted != flag);
    }
}
