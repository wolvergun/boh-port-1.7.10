package net.mcreator.boh.compat.mc.core.particles;

import net.minecraft.util.ResourceLocation;

public class ParticleType<T extends ParticleOptions> {
    private final boolean overrideLimiter;
    private ResourceLocation id;
    private String legacyName;

    protected ParticleType(boolean overrideLimiter) {
        this.overrideLimiter = overrideLimiter;
    }

    public boolean getOverrideLimiter() {
        return this.overrideLimiter;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }

    public String legacyName() {
        return this.legacyName;
    }

    public ParticleType<T> withLegacyName(String name) {
        this.legacyName = name;
        return this;
    }
}
