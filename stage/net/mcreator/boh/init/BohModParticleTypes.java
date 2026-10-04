package net.mcreator.boh.init;

import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegistryObject;

public class BohModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "boh");

    public static final RegistryObject<SimpleParticleType> MOTHMANSONICATTACKPARTICLE = REGISTRY.register("mothmansonicattackparticle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> GRAY_PARTICLE = REGISTRY.register("gray_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> PAINTED_SWORD_SWEEP = REGISTRY.register("painted_sword_sweep", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> CONFETTI = REGISTRY.register("confetti", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BLOOD_FALL = REGISTRY.register("blood_fall", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ANGLER_PARTICLE = REGISTRY.register("angler_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> RIFT_TEAR_PARTICLE = REGISTRY.register("rift_tear_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BITE_PARTICLE = REGISTRY.register("bite_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> GOLD_HIT = REGISTRY.register("gold_hit", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> REND_SWEEP = REGISTRY.register("rend_sweep", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> HYPNO_SHOT = REGISTRY.register("hypno_shot", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> MIMICRY_SWEEP = REGISTRY.register("mimicry_sweep", () -> new SimpleParticleType(false));
}
