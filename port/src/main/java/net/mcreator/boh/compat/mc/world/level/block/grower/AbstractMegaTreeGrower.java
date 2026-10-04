package net.mcreator.boh.compat.mc.world.level.block.grower;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.util.RandomSource;

/** 1.20 AbstractMegaTreeGrower (2x2 variant grows like the single tree here). */
public abstract class AbstractMegaTreeGrower extends AbstractTreeGrower {

    protected abstract ResourceKey<?> getConfiguredMegaFeature(RandomSource random);

    @Override
    protected ResourceKey<?> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return null;
    }
}
