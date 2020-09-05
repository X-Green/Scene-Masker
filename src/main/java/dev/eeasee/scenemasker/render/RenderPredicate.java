package dev.eeasee.scenemasker.render;

import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class RenderPredicate implements Predicate<BlockPos> {

    @Override
    public boolean test(BlockPos blockPos) {
        return false;
    }

    public static RenderPredicate getWorldMaskerPredicate() {
        return new RenderPredicate();
    }
}
