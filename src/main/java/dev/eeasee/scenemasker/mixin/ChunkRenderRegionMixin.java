package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.utils.MaskerWorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRendererRegion.class)
public abstract class ChunkRenderRegionMixin implements BlockRenderView {

    @Shadow
    @Final
    protected World world;

    @Inject(method = "getBlockState", at = @At("HEAD"), cancellable = true)
    private void getMaskedBlockState(BlockPos blockPos, CallbackInfoReturnable<BlockState> cir) {
        if (MaskerWorldUtils.shouldHideBlock(blockPos, world)) {
            cir.setReturnValue(MaskerWorldUtils.AIR_BLOCK_STATE);
        }
    }
}
