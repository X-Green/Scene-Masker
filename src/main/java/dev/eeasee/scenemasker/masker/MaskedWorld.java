package dev.eeasee.scenemasker.masker;

import com.google.common.collect.Maps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

public class MaskedWorld {
    private Map<ChunkPos, MaskedChunk> chunkMap = Maps.newHashMap();

    public MaskedChunk getMaskedChunk(ChunkPos chunkPos) {
        return chunkMap.getOrDefault(chunkPos, MaskedChunk.EMPTY);
    }

    public MaskedChunk getMaskedChunk(BlockPos blockPos) {
        return getMaskedChunk(new ChunkPos(blockPos));
    }

    public boolean isChunkEmpty(ChunkPos chunkPos) {
        return chunkMap.containsKey(chunkPos);
    }

    public boolean isChunkEmpty(BlockPos blockPos) {
        return isChunkEmpty(new ChunkPos(blockPos));
    }

    public boolean isBlockMasked(BlockPos blockPos) {
        return this.getMaskedChunk(blockPos).getMaskBooleanState(blockPos);
    }
}
