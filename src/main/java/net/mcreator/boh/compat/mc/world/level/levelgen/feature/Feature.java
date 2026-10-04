package net.mcreator.boh.compat.mc.world.level.levelgen.feature;

import net.minecraft.util.ResourceLocation;

public abstract class Feature<C> {
    private ResourceLocation id;

    public ResourceLocation getId() {
        return this.id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }
}
