package dev.eeasee.scenemasker.utils;

import com.google.common.collect.Sets;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.HashSet;
import java.util.Set;

public class MaskedChunk {
    public final ChunkPos chunkPos;



    public MaskedChunk(ChunkPos chunkPos, Set<BlockPos> blocks) {

    }

    public MaskedChunk(ChunkPos chunkPos) {
        MaskedChunk(chunkPos, new HashSet<>())
    }
}
