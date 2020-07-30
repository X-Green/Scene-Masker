package dev.eeasee.scenemasker.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockState.class)
public abstract class BlockStateMixin {
    @Inject(method = "getRenderType",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getRenderType(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockRenderType;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void masked(CallbackInfoReturnable<BlockRenderType> cir) {
        cir.setReturnValue(BlockRenderType.INVISIBLE);
    }
}
