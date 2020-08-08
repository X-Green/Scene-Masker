package dev.eeasee.scenemasker.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class MaskerWorldUtils {

    public final static BlockState AIR_BLOCK_STATE = Blocks.AIR.getDefaultState();

    public static boolean shouldHideBlock(BlockPos blockPos, WorldChunk chunk) {

    }

    public static boolean shouldHideBlock(int inChunkPosX, int inChunkPosZ, int height, WorldChunk chunk) {

    }

    public static boolean shouldHideBlock(BlockPos blockPos, World world) {
        WorldChunk chunk = world.getWorldChunk(blockPos);
        return shouldHideBlock(blockPos, chunk);
    }
}
