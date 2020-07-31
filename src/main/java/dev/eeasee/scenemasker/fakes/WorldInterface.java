package dev.eeasee.scenemasker.fakes;

import dev.eeasee.scenemasker.utils.MaskProperties;
import dev.eeasee.scenemasker.world.MaskedWorld;

public interface WorldInterface {
    MaskedWorld getWorldMasker();
    MaskProperties getMaskProperties();
}