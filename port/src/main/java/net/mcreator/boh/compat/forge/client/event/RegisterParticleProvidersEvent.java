package net.mcreator.boh.compat.forge.client.event;

import java.util.function.Function;

import net.mcreator.boh.compat.client.particle.ParticleEngine;
import net.mcreator.boh.compat.mc.client.particle.SpriteSet;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 RegisterParticleProvidersEvent. */
public class RegisterParticleProvidersEvent extends Event {

    public <T> void registerSpriteSet(ParticleType<?> type, Function<SpriteSet, ? extends net.mcreator.boh.compat.mc.client.particle.ParticleProvider<T>> provider) {
        ParticleEngine.registerSpriteSet(type, provider);
    }
}
