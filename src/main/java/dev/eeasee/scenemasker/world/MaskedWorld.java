package dev.eeasee.scenemasker.world;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.eeasee.scenemasker.Masker;
import dev.eeasee.scenemasker.network.data.s2c.ChunkSectionUpdateData;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class MaskedWorld {
    private Map<ChunkPos, MaskedChunk> chunkMap = Maps.newHashMap();

    private Set<ChunkPos> changeSet = Sets.newHashSet();

    MaskedChunk getMaskedChunkOrEmpty(ChunkPos chunkPos) {
        return chunkMap.getOrDefault(chunkPos, MaskedChunk.EMPTY);
    }

    MaskedChunk getMaskedChunkOrEmpty(BlockPos blockPos) {
        return getMaskedChunkOrEmpty(new ChunkPos(blockPos));
    }

    MaskedChunk getMaskedChunkOrNew(ChunkPos chunkPos) {
        if (chunkMap.containsKey(chunkPos)) {
            return chunkMap.get(chunkPos);
        } else {
            MaskedChunk newChunk = new MaskedChunk(chunkPos);
            chunkMap.put(chunkPos, newChunk);
            return newChunk;
        }
    }

    MaskedSection getSectionOrEmpty(ChunkSectionPos sectionPos) {
        return getMaskedChunkOrEmpty(sectionPos.toChunkPos()).getSectionOrEmpty(sectionPos.getSectionY());
    }

    MaskedChunk getMaskedChunkOrNew(BlockPos blockPos) {
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
                deleteChunk(chunkPos);
            }
        }
    }

    void deleteChunk(ChunkPos chunkPos) {
        this.chunkMap.remove(chunkPos);
        this.changeSet.add(chunkPos);
    }

    public boolean isBlockMasked(BlockPos blockPos) {
        return this.getMaskedChunkOrEmpty(blockPos).getMaskBooleanState(blockPos);
    }

    public void setBlockMasked(BlockPos blockPos, boolean value) {
        this.getMaskedChunkOrNew(blockPos).setMaskBooleanState(blockPos, value);
        this.changeSet.add(new ChunkPos(blockPos));
    }

    public void setSectionMasked(ChunkSectionPos sectionPos, boolean[] values) {
        if (values.length != 4096) {
            Masker.LOGGER.error("Unsupported section data for masker!");
            return;
        }
        this.getMaskedChunkOrNew(sectionPos.toChunkPos()).setSection(values, sectionPos.getSectionY());
        this.changeSet.add(sectionPos.toChunkPos());
    }

    public void cleanEmptyChunks() {
        chunkMap.keySet().forEach(this::deleteChunkIfEmpty);
    }

    public void flushChunkChangeSet(Consumer<ChunkPos> consumer) {
        changeSet.forEach(consumer);
        changeSet.clear();
    }

    public ChunkSectionUpdateData createChunkSectionUpdateData(ChunkSectionPos sectionPos) {
        MaskedSection section = this.getSectionOrEmpty(sectionPos);
        return new ChunkSectionUpdateData(section);
    }
}
