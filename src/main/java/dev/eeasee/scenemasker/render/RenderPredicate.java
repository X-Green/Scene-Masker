package dev.eeasee.scenemasker.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Predicate;

public class RenderPredicate implements Predicate<BlockPos> {

    @Override
    public boolean test(BlockPos blockPos) {
        return false;
    }

    public static RenderPredicate getWorldMaskerPredicate(BlockView blockView) {
        return new RenderPredicate();
    }
}
