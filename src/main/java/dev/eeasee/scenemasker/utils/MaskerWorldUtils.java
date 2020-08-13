package dev.eeasee.scenemasker.utils;

import dev.eeasee.scenemasker.chunk.MaskedChunk;
import dev.eeasee.scenemasker.chunk.MaskedSection;
import dev.eeasee.scenemasker.fakes.WorldChunkInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

public class MaskerWorldUtils {

    public final static BlockState AIR_BLOCK_STATE = Blocks.AIR.getDefaultState();

    private static boolean shouldHideBlock(BlockPos blockPos, ChunkSection section) {
        return false;
    }

    public static boolean shouldHideBlock(BlockPos blockPos, WorldChunk chunk) {
        if (blockPos.getY() < 0 || blockPos.getY() > 255) {
            return false;
        }
        return shouldHideBlock(blockPos, chunk.getSectionArray()[blockPos.getY() >> 4]);
    }

    public static boolean shouldHideBlock(BlockPos blockPos, World world) {
        WorldChunk chunk = world.getWorldChunk(blockPos);
        return shouldHideBlock(blockPos, chunk);
    }

    public static void setBlockMaskerState(World world, BlockPos pos, boolean value) {
        MaskedChunk maskedChunk = ((WorldChunkInterface) world.getWorldChunk(pos)).getMaskedChunk();
        MaskedSection maskedSection = maskedChunk.getMaskedSection(pos);
        maskedSection.setMaskerState(pos.getX(), pos.getY(), pos.getZ(), value);
    }

    public static void setBoxedBlockMaskerStates(World world, BlockBox box, boolean value) {
        BoxUtils.consumeAsDividedBoxes(box, 16, ((secPos, isFullInnerBox, blockBox) -> {
            MaskedChunk maskedChunk = ((WorldChunkInterface) world.getChunk(secPos.getX(), secPos.getZ())).getMaskedChunk();
            MaskedSection maskedSection = maskedChunk.getMaskedSection(secPos.getY());
            if (isFullInnerBox) {
                maskedSection.setMaskerStateAll(value);
            } else {
                BoxUtils.forEachInBox(blockBox, (x, y, z) ->
                        maskedSection.setMaskerState(x, y, z, value, false));
                maskedSection.doCheck();
            }
        }), true);
    }

    public static void revertBlockMaskerState(World world, BlockPos pos) {
        MaskedChunk maskedChunk = ((WorldChunkInterface) world.getWorldChunk(pos)).getMaskedChunk();
        MaskedSection maskedSection = maskedChunk.getMaskedSection(pos);
        maskedSection.setMaskerState(pos.getX(), pos.getY(), pos.getZ(), !maskedSection.getMaskerState(pos));
    }

    public static void revertBoxedBlockMaskerStates(World world, BlockBox box) {
        BoxUtils.consumeAsDividedBoxes(box, 16, ((secPos, isFullInnerBox, blockBox) -> {
            MaskedChunk maskedChunk = ((WorldChunkInterface) world.getChunk(secPos.getX(), secPos.getZ())).getMaskedChunk();
            MaskedSection maskedSection = maskedChunk.getMaskedSection(secPos.getY());
            BoxUtils.forEachInBox(blockBox, (x, y, z) ->
                    maskedSection.setMaskerState(x, y, z, !maskedSection.getMaskerState(x, y, z), false));
            maskedSection.doCheck();
        }), true);
    }

    public static boolean getBlockMaskerState(World world, BlockPos pos) {
        MaskedChunk maskedChunk = ((WorldChunkInterface) world.getWorldChunk(pos)).getMaskedChunk();
        MaskedSection maskedSection = maskedChunk.getMaskedSection(pos);
        return maskedSection.getMaskerState(pos);
    }

    public static void setSectionMaskerState(World world, ChunkSectionPos sectionPos, boolean[] booleans) {
        MaskedChunk maskedChunk = ((WorldChunkInterface) world.getChunk(sectionPos.getX(), sectionPos.getZ())).getMaskedChunk();
        MaskedSection maskedSection = maskedChunk.getMaskedSection(sectionPos.getSectionY());
        maskedSection.setMaskerStates(booleans);
    }
}
