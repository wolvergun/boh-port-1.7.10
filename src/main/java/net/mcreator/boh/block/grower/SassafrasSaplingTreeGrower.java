package net.mcreator.boh.block.grower;

import net.mcreator.boh.compat.mc.data.worldgen.features.FeatureUtils;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.grower.AbstractTreeGrower;
import net.mcreator.boh.compat.mc.world.level.levelgen.feature.ConfiguredFeature;

public class SassafrasSaplingTreeGrower extends AbstractTreeGrower {
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean hasFlower) {
        return FeatureUtils.createKey("boh:sasafras_tree_feature");
    }
}
