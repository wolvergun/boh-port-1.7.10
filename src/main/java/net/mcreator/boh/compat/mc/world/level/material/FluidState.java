package net.mcreator.boh.compat.mc.world.level.material;

import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class FluidState {
    private final Fluid fluid;
    private final boolean source;

    FluidState(Fluid fluid, boolean source) {
        this.fluid = fluid;
        this.source = source;
    }

    public static FluidState of(Block block, int meta) {
        Material m = block.getMaterial();
        if (m == Material.water) {
            return new FluidState(Fluids.WATER, meta == 0);
        } else {
            return m == Material.lava ? new FluidState(Fluids.LAVA, meta == 0) : new FluidState(Fluids.EMPTY, false);
        }
    }

    public boolean isEmpty() {
        return this.fluid == Fluids.EMPTY;
    }

    public boolean isSource() {
        return this.source;
    }

    public Fluid getType() {
        return this.fluid;
    }

    public boolean is(Fluid f) {
        return this.fluid == f;
    }

    public int getAmount() {
        return this.isEmpty() ? 0 : (this.source ? 8 : 7);
    }

    public BlockState createLegacyBlock() {
        return BlockState.of(this.fluid.still, 0);
    }
}
