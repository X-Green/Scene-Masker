package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.render.RenderPredicate;
import dev.eeasee.scenemasker.utils.BlockPosUtil;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public abstract class ChunkBuilderRebuildTaskMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;iterate(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;"))
    private Iterable newIteratorForNonMaskedBlocks(BlockPos pos1, BlockPos pos2) {
        return BlockPosUtil.iterableWithPredicate(pos1, pos2, RenderPredicate.getWorldMaskerPredicate());
    }
}
