package net.mcreator.boh.client.particle;

import net.minecraft.client.multiplayer.WorldClient;
import net.mcreator.boh.compat.mc.client.particle.Particle;
import net.mcreator.boh.compat.mc.client.particle.ParticleProvider;
import net.mcreator.boh.compat.mc.client.particle.ParticleRenderType;
import net.mcreator.boh.compat.mc.client.particle.SpriteSet;
import net.mcreator.boh.compat.mc.client.particle.TextureSheetParticle;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.M;

@SideOnly(Side.CLIENT)
public class BiteParticleParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    public static BiteParticleParticle.BiteParticleParticleProvider provider(SpriteSet spriteSet) {
        return new BiteParticleParticle.BiteParticleParticleProvider(spriteSet);
    }

    protected BiteParticleParticle(WorldClient world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        M.setSize(this, 0.2F, 0.2F);
        this.quadSize *= 10.0F;
        this.lifetime = Math.max(1, 9 + (M.nextInt(this.random, 18) - 9));
        this.gravity = 0.0F;
        this.hasPhysics = true;
        this.xd = vx * 0.0;
        this.yd = vy * 0.0;
        this.zd = vz * 0.0;
        M.setSpriteFromAge(this, spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            M.setSprite(this, this.spriteSet.get(this.age / 1 % 9 + 1, 9));
        }
    }

    public static class BiteParticleParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public BiteParticleParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, WorldClient worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BiteParticleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
