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
import net.mcreator.boh.procedures.AnglerParticleParticleVisualScaleProcedure;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class AnglerParticleParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public static AnglerParticleParticle.AnglerParticleParticleProvider provider(SpriteSet spriteSet) {
        return new AnglerParticleParticle.AnglerParticleParticleProvider(spriteSet);
    }

    protected AnglerParticleParticle(WorldClient world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        M.setSize(this, 0.0F, 0.0F);
        this.lifetime = Math.max(1, 2 + (M.nextInt(this.random, 4) - 2));
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = vx * 0.0;
        this.yd = vy * 0.0;
        this.zd = vz * 0.0;
        M.pickSprite(this, spriteSet);
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
    public float getQuadSize(float scale) {
        World world = M.level(this);
        return super.getQuadSize(scale) * (float)AnglerParticleParticleVisualScaleProcedure.execute();
    }

    @Override
    public void tick() {
        super.tick();
    }

    public static class AnglerParticleParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public AnglerParticleParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(
            SimpleParticleType typeIn, WorldClient worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new AnglerParticleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
