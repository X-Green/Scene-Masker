package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.fakes.WorldInterface;
import dev.eeasee.scenemasker.utils.MaskProperties;
import dev.eeasee.scenemasker.world.MaskedWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
public abstract class WorldMixin implements IWorld, WorldInterface {

    private final MaskedWorld WORLD_MASKER = new MaskedWorld();

    private final MaskProperties MASK_PROPERTIES = new MaskProperties();

    @Override
    public MaskedWorld getWorldMasker() {
        return this.WORLD_MASKER;
    }

    @Override
    public MaskProperties getMaskProperties() {
        return this.MASK_PROPERTIES;
    }
}
