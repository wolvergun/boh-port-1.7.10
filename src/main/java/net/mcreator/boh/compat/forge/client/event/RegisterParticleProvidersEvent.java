package net.mcreator.boh.compat.forge.client.event;

import cpw.mods.fml.common.eventhandler.Event;
import java.util.function.Function;
import net.mcreator.boh.compat.client.particle.ParticleEngine;
import net.mcreator.boh.compat.mc.client.particle.ParticleProvider;
import net.mcreator.boh.compat.mc.client.particle.SpriteSet;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;

public class RegisterParticleProvidersEvent extends Event {
    public <T> void registerSpriteSet(ParticleType<?> type, Function<SpriteSet, ? extends ParticleProvider<T>> provider) {
        ParticleEngine.registerSpriteSet(type, provider);
    }
}
