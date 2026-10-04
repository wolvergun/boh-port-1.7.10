package net.mcreator.boh.compat.mc.core.particles;

public class SimpleParticleType extends ParticleType<SimpleParticleType> implements ParticleOptions {
    public SimpleParticleType(boolean overrideLimiter) {
        super(overrideLimiter);
    }

    public SimpleParticleType getType() {
        return this;
    }
}
