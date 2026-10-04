package net.mcreator.boh.compat.mc.core.particles;

import net.minecraft.util.ResourceLocation;

/** 1.20 ParticleType; vanilla ones carry the 1.7.10 particle name, mod ones are rendered by the port. */
public class ParticleType<T extends ParticleOptions> {

    private final boolean overrideLimiter;
    private ResourceLocation id;
    private String legacyName;

    protected ParticleType(boolean overrideLimiter) {
        this.overrideLimiter = overrideLimiter;
    }

    public boolean getOverrideLimiter() {
        return overrideLimiter;
    }

    public ResourceLocation getId() {
        return id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }

    /** Name for World.spawnParticle when this is a vanilla particle, else null. */
    public String legacyName() {
        return legacyName;
    }

    public ParticleType<T> withLegacyName(String name) {
        legacyName = name;
        return this;
    }
}
