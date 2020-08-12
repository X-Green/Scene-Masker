package dev.eeasee.scenemasker.utils;

import dev.eeasee.scenemasker.fakes.ChunkSectionInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

public class MaskerWorldUtils {

    public final static BlockState AIR_BLOCK_STATE = Blocks.AIR.getDefaultState();

    private static boolean shouldHideBlock(BlockPos blockPos, ChunkSection section) {
        return false;
    }

    public static boolean shouldHideBlock(BlockPos blockPos, WorldChunk chunk) {
        return shouldHideBlock(blockPos, chunk.getSectionArray()[blockPos.getY() >> 4]);
    }

    public static boolean shouldHideBlock(BlockPos blockPos, World world) {
        WorldChunk chunk = world.getWorldChunk(blockPos);
        return shouldHideBlock(blockPos, chunk);
    }

    public static void setBlockMaskerState(World world, BlockPos pos, boolean value) {
        Chunk chunk = world.getChunk(pos);
        ChunkSectionInterface sectionInterface = (ChunkSectionInterface) chunk.getSectionArray()[pos.getY()];
        sectionInterface.setMaskerState(pos.getX(), pos.getY(), pos.getZ(), value);
    }

    public static void setBoxedBlockMaskerStates(World world, BlockBox box, boolean value) {
        BoxUtils.consumeAsDividedBoxes(box, 16, ((secPos, isFullInnerBox, blockBox) -> {
            ChunkSectionInterface sectionInterface = (ChunkSectionInterface) world.getChunk(secPos.getX(), secPos.getZ()).getSectionArray()[secPos.getY()];
            if (isFullInnerBox) {
                sectionInterface.setMaskerStateAll(value);
            } else {
                BoxUtils.forEachInBox(blockBox, (x, y, z) ->
                        sectionInterface.setMaskerState(x, y, z, value, false));
                sectionInterface.doCheck();
            }
        }), true);
    }

    public static void revertBlockMaskerState(World world, BlockPos pos) {
        Chunk chunk = world.getChunk(pos);
        ChunkSectionInterface sectionInterface = (ChunkSectionInterface) chunk.getSectionArray()[pos.getY()];
        sectionInterface.setMaskerState(pos.getX(), pos.getY(), pos.getZ(), !sectionInterface.getMaskerState(pos));
    }

    public static void revertBoxedBlockMaskerStates(World world, BlockBox box) {
        BoxUtils.consumeAsDividedBoxes(box, 16, ((secPos, isFullInnerBox, blockBox) -> {
            ChunkSectionInterface sectionInterface = (ChunkSectionInterface) world.getChunk(secPos.getX(), secPos.getZ()).getSectionArray()[secPos.getY()];
            BoxUtils.forEachInBox(blockBox, (x, y, z) ->
                    sectionInterface.setMaskerState(x, y, z, !sectionInterface.getMaskerState(x, y, z), false));
            sectionInterface.doCheck();
        }), true);
    }

    public static boolean getBlockMaskerState(World world, BlockPos pos) {
        Chunk chunk = world.getChunk(pos);
        ChunkSectionInterface sectionInterface = (ChunkSectionInterface) chunk.getSectionArray()[pos.getY()];
        return sectionInterface.getMaskerState(pos);
    }

    public static void setSectionMaskerState(World world, ChunkSectionPos sectionPos, boolean[] booleans) {
        Chunk chunk = world.getChunk(sectionPos.getX(), sectionPos.getZ());
        ChunkSectionInterface sectionInterface = (ChunkSectionInterface) chunk.getSectionArray()[sectionPos.getSectionY()];
        sectionInterface.setMaskerStates(booleans);
    }
}
