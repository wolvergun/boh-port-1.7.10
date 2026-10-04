package net.mcreator.boh.block.grower;

import net.mcreator.boh.compat.mc.data.worldgen.features.FeatureUtils;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.grower.AbstractMegaTreeGrower;
import net.mcreator.boh.compat.mc.world.level.levelgen.feature.ConfiguredFeature;

public class BlackWallnutSapplingTreeGrower extends AbstractMegaTreeGrower {
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean hasFlower) {
        return null;
    }

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource randomSource) {
        return FeatureUtils.createKey("boh:black_wallnut_tree_feature");
    }
}
