package net.mcreator.boh.compat.mc.world.level.material;

import net.minecraft.block.Block;

public final class Fluid {
    final String name;
    final Block still;

    Fluid(String name, Block still) {
        this.name = name;
        this.still = still;
    }

    public boolean isSame(Fluid other) {
        return other == this;
    }

    public FluidState defaultFluidState() {
        return new FluidState(this, true);
    }

    public FluidState getSource(boolean falling) {
        return new FluidState(this, true);
    }

    public int getTickDelay(Object world) {
        return this == Fluids.LAVA ? 30 : 5;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
