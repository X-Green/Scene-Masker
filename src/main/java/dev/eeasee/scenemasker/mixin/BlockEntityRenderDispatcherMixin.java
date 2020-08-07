package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.client.MaskProperties;
import dev.eeasee.scenemasker.utils.MaskerWorldUtils;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {
    @Shadow public World world;

    @Inject(method = "render(Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"),
            cancellable = true
    )
    private static <T extends BlockEntity> void cancelIfMasked(BlockEntityRenderer<T> renderer, T blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        WorldInterface worldInterface = (WorldInterface) blockEntity.getWorld();
        MaskedWorld worldMasker = worldInterface.getWorldMasker();
        MaskProperties maskProperties = worldInterface.getMaskProperties();
        if (MaskerWorldUtils.isBlockRenderedMasked_OLD(blockEntity.getPos(), worldMasker, maskProperties)) {
            ci.cancel();
        }
    }
}
