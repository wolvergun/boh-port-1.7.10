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
public class HypnoShotParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    public static HypnoShotParticle.HypnoShotParticleProvider provider(SpriteSet spriteSet) {
        return new HypnoShotParticle.HypnoShotParticleProvider(spriteSet);
    }

    protected HypnoShotParticle(WorldClient world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        M.setSize(this, 0.2F, 0.2F);
        this.quadSize *= 2.0F;
        this.lifetime = 14;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = vx * 1.0;
        this.yd = vy * 1.0;
        this.zd = vz * 1.0;
        M.setSpriteFromAge(this, spriteSet);
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            M.setSprite(this, this.spriteSet.get(this.age / 1 % 16 + 1, 16));
        }
    }

    public static class HypnoShotParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public HypnoShotParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, WorldClient worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new HypnoShotParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
