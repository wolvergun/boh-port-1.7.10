package net.mcreator.boh.compat.mc.world.level.levelgen.feature;

import net.minecraft.util.ResourceLocation;

/** 1.20 worldgen Feature; concrete features are implemented by the port's worldgen package. */
public abstract class Feature<C> {

    private ResourceLocation id;

    public ResourceLocation getId() {
        return id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }
}
