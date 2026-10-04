package net.mcreator.boh.compat.mc.world.level.material;

import net.minecraft.init.Blocks;

public final class Fluids {
    public static final Fluid EMPTY = new Fluid("empty", Blocks.air);
    public static final Fluid WATER = new Fluid("water", Blocks.water);
    public static final Fluid FLOWING_WATER = WATER;
    public static final Fluid LAVA = new Fluid("lava", Blocks.lava);
    public static final Fluid FLOWING_LAVA = LAVA;

    private Fluids() {
    }
}
