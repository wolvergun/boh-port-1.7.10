package net.mcreator.boh.compat.mc.client.particle;

import net.minecraft.client.multiplayer.WorldClient;

@FunctionalInterface
public interface ParticleProvider<T> {

    Particle createParticle(T type, WorldClient world, double x, double y, double z, double dx, double dy, double dz);
}
