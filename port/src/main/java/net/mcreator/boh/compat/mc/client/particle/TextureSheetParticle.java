package net.mcreator.boh.compat.mc.client.particle;

import net.minecraft.client.multiplayer.WorldClient;

public class TextureSheetParticle extends Particle {

    protected TextureSheetParticle(WorldClient world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected TextureSheetParticle(WorldClient world, double x, double y, double z, double dx, double dy, double dz) {
        super(world, x, y, z, dx, dy, dz);
    }
}
