package dev.eeasee.scenemasker.masker;

import com.google.common.collect.Maps;
import dev.eeasee.scenemasker.masker.chunk.MaskedChunk;
import dev.eeasee.scenemasker.masker.chunk.MaskedEmptyChunk;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

public class MaskedWorld {
    private Map<ChunkPos, MaskedChunk> chunkMap = Maps.newHashMap();

    public MaskedChunk getMaskedChunk(ChunkPos chunkPos) {
        return chunkMap.getOrDefault(chunkPos, MaskedEmptyChunk.getInstance());
    }



}
