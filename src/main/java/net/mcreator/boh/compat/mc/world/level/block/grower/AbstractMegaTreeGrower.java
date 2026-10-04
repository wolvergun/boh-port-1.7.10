package net.mcreator.boh.compat.mc.world.level.block.grower;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.util.RandomSource;

public abstract class AbstractMegaTreeGrower extends AbstractTreeGrower {
    protected abstract ResourceKey<?> getConfiguredMegaFeature(RandomSource var1);

    @Override
    protected ResourceKey<?> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return null;
    }
}
