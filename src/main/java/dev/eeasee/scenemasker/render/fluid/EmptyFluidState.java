package dev.eeasee.scenemasker.render.fluid;

import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidStateImpl;

public class EmptyFluidState extends FluidStateImpl {
    private final static EmptyFluidState INSTANCE = new EmptyFluidState();
    private Fluid fluid = new EmptyFluid();

    private EmptyFluidState() {
        super(new EmptyFluid(), null);
    }

    @Override
    public Fluid getFluid() {
        return fluid;
    }

    public static EmptyFluidState getInstance() {
        return INSTANCE;
    }
}
