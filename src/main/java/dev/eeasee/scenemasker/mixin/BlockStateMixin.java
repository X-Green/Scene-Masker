package dev.eeasee.scenemasker.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockState.class)
public class BlockStateMixin {
    @Redirect(method = "getRenderType", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/block/Block;getRenderType(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockRenderType;"
    ))
    private BlockRenderType masked(Block block, BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
