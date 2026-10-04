package net.mcreator.boh.compat.mc.world.level.material;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/** 1.20 FluidState for a block position. */
public final class FluidState {

    private final Fluid fluid;
    private final boolean source;

    FluidState(Fluid fluid, boolean source) {
        this.fluid = fluid;
        this.source = source;
    }

    public static FluidState of(Block block, int meta) {
        Material m = block.getMaterial();
        if (m == Material.water) return new FluidState(Fluids.WATER, meta == 0);
        if (m == Material.lava) return new FluidState(Fluids.LAVA, meta == 0);
        return new FluidState(Fluids.EMPTY, false);
    }

    public boolean isEmpty() {
        return fluid == Fluids.EMPTY;
    }

    public boolean isSource() {
        return source;
    }

    public Fluid getType() {
        return fluid;
    }

    public boolean is(Fluid f) {
        return fluid == f;
    }

    public int getAmount() {
        return isEmpty() ? 0 : source ? 8 : 7;
    }

    public net.mcreator.boh.compat.mc.world.level.block.state.BlockState createLegacyBlock() {
        return net.mcreator.boh.compat.mc.world.level.block.state.BlockState.of(fluid.still, 0);
    }
}
