package dev.eeasee.scenemasker.chunk;

import net.minecraft.util.math.BlockPos;

public class MaskedChunk {

    private MaskedSection[] sections = new MaskedSection[16];

    public MaskedSection getMaskedSection(int index) {
        return sections[index];
    }

    public MaskedSection getMaskedSection(BlockPos blockPos) {
        return getMaskedSection(blockPos.getY() >> 4);
    }
}
