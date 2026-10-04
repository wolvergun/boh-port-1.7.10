package net.mcreator.boh.compat.mc.client.particle;

import net.minecraft.client.multiplayer.WorldClient;

@FunctionalInterface
public interface ParticleProvider<T> {
    Particle createParticle(T var1, WorldClient var2, double var3, double var5, double var7, double var9, double var11, double var13);
}
