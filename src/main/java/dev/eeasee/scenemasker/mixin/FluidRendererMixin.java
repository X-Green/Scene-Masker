package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.render.RenderPredicate;
import dev.eeasee.scenemasker.render.fluid.EmptyFluidState;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {
    @Redirect(method = "isSameFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/BlockView;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;"))
    private static FluidState maskedFluidState(BlockView blockView, BlockPos pos) {
        if (!RenderPredicate.getWorldMaskerPredicate(blockView).test(pos)) {
            return EmptyFluidState.getInstance();
        } else {
            return blockView.getFluidState(pos);
        }
    }

    @Redirect(method = "isSideCovered", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/BlockView;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    private static BlockState maskedBlockState(BlockView blockView, BlockPos pos) {
        if (!RenderPredicate.getWorldMaskerPredicate(blockView).test(pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return blockView.getBlockState(pos);
        }
    }
}
