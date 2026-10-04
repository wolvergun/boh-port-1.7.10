package net.mcreator.boh.compat.mc.world.level.block.grower;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.world.TreePlacer;
import net.minecraft.world.World;

/** 1.20 AbstractTreeGrower: grows the configured tree feature named by the subclass. */
public abstract class AbstractTreeGrower {

    protected abstract ResourceKey<?> getConfiguredFeature(RandomSource random, boolean hasFlowers);

    public boolean growTree(World world, Object generator, BlockPos pos, BlockState state, RandomSource random) {
        ResourceKey<?> key = getConfiguredFeature(random, false);
        if (key == null) return false;
        world.setBlockToAir(pos.getX(), pos.getY(), pos.getZ());
        if (TreePlacer.place(key.location(), world, pos, random)) return true;
        net.mcreator.boh.compat.M.setBlock(world, pos, state, 4);
        return false;
    }
}
