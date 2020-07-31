package dev.eeasee.scenemasker.world;

import com.google.common.collect.Maps;
import dev.eeasee.scenemasker.Masker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;

import java.util.Map;

public class MaskedWorld {
    private Map<ChunkPos, MaskedChunk> chunkMap = Maps.newHashMap();

    private MaskedChunk getMaskedChunk(ChunkPos chunkPos) {
        return chunkMap.getOrDefault(chunkPos, MaskedChunk.EMPTY);
    }

    private MaskedChunk getMaskedChunk(BlockPos blockPos) {
        return getMaskedChunk(new ChunkPos(blockPos));
    }

    private MaskedChunk getMaskedChunkOrNew(ChunkPos chunkPos) {
        if (chunkMap.containsKey(chunkPos)) {
            return chunkMap.get(chunkPos);
        } else {
            MaskedChunk newChunk = new MaskedChunk(chunkPos);
            chunkMap.put(chunkPos, newChunk);
            return newChunk;
        }
    }

    private MaskedChunk getMaskedChunkOrNew(BlockPos blockPos) {
        return getMaskedChunkOrNew(new ChunkPos(blockPos));
    }

    public boolean containsChunk(ChunkPos chunkPos) {
        return chunkMap.containsKey(chunkPos);
    }

    public boolean containsChunk(BlockPos blockPos) {
        return containsChunk(new ChunkPos(blockPos));
    }

    public void deleteChunkIfEmpty(ChunkPos chunkPos) {
        if (this.containsChunk(chunkPos)) {
            MaskedChunk chunk = this.chunkMap.get(chunkPos);
            if (chunk.isEmptyChunk()) {
                this.chunkMap.remove(chunkPos);
            }
        }
    }

    public void deleteChunk(ChunkPos chunkPos) {
        this.chunkMap.remove(chunkPos);
    }

    public boolean isBlockMasked(BlockPos blockPos) {
        return this.getMaskedChunk(blockPos).getMaskBooleanState(blockPos);
    }

    public void setBlockMasked(BlockPos blockPos, boolean value) {
        this.getMaskedChunkOrNew(blockPos).setMaskBooleanState(blockPos, value);
    }

    public void setSectionMasked(ChunkSectionPos sectionPos, boolean[] values) {
        if (values.length != 4096) {
            Masker.LOGGER.error("Unsupported section data for masker!");
            return;
        }
        this.getMaskedChunkOrNew(sectionPos.toChunkPos()).setSection(values, sectionPos.getSectionY());
    }

    public void cleanEmptyChunks() {
        chunkMap.keySet().forEach(this::deleteChunkIfEmpty);
    }
}
