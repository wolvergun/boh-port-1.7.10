package net.mcreator.boh.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.client.particle.Particle;
import net.mcreator.boh.compat.mc.client.particle.ParticleProvider;
import net.mcreator.boh.compat.mc.client.particle.ParticleRenderType;
import net.mcreator.boh.compat.mc.client.particle.SpriteSet;
import net.mcreator.boh.compat.mc.client.particle.TextureSheetParticle;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.minecraft.client.multiplayer.WorldClient;

@SideOnly(Side.CLIENT)
public class MimicrySweepParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public static MimicrySweepParticle.MimicrySweepParticleProvider provider(SpriteSet spriteSet) {
        return new MimicrySweepParticle.MimicrySweepParticleProvider(spriteSet);
    }

    protected MimicrySweepParticle(WorldClient world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        M.setSize(this, 0.4F, 0.4F);
        this.quadSize *= 6.0F;
        this.lifetime = 7;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = vx * 1.0;
        this.yd = vy * 1.0;
        this.zd = vz * 1.0;
        M.setSpriteFromAge(this, spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            M.setSprite(this, this.spriteSet.get(this.age / 1 % 8 + 1, 8));
        }
    }

    public static class MimicrySweepParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public MimicrySweepParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(
            SimpleParticleType typeIn, WorldClient worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new MimicrySweepParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
