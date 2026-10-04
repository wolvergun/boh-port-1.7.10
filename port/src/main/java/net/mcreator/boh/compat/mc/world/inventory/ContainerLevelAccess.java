package net.mcreator.boh.compat.mc.world.inventory;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;

/** 1.20 ContainerLevelAccess. */
public final class ContainerLevelAccess {

    public static final ContainerLevelAccess NULL = new ContainerLevelAccess(null, null);

    public final World world;
    public final BlockPos pos;

    private ContainerLevelAccess(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public static ContainerLevelAccess create(World w, BlockPos p) {
        return new ContainerLevelAccess(w, p);
    }
}
