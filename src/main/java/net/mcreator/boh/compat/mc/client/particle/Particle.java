package net.mcreator.boh.compat.mc.client.particle;

import java.util.Random;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class Particle extends EntityFX {
    public double x;
    public double y;
    public double z;
    public double xo;
    public double yo;
    public double zo;
    public double xd;
    public double yd;
    public double zd;
    public int age;
    public int lifetime;
    public float gravity;
    public float friction = 0.98F;
    public float quadSize;
    public float rCol = 1.0F;
    public float gCol = 1.0F;
    public float bCol = 1.0F;
    public float alpha = 1.0F;
    public float roll;
    public float oRoll;
    public boolean hasPhysics = true;
    public boolean removed;
    public boolean speedUpWhenYMotionIsBlocked;
    public final Random random = new Random();
    protected IIcon sprite;
    protected float bbWidth = 0.6F;
    protected float bbHeight = 1.8F;

    protected Particle(WorldClient world, double x, double y, double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.x = this.xo = x;
        this.y = this.yo = y;
        this.z = this.zo = z;
        this.quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
        this.lifetime = (int)(4.0F / (this.random.nextFloat() * 0.9F + 0.1F));
        this.motionX = this.motionY = this.motionZ = 0.0;
    }

    protected Particle(WorldClient world, double x, double y, double z, double dx, double dy, double dz) {
        this(world, x, y, z);
        this.xd = dx + (Math.random() * 2.0 - 1.0) * 0.4;
        this.yd = dy + (Math.random() * 2.0 - 1.0) * 0.4;
        this.zd = dz + (Math.random() * 2.0 - 1.0) * 0.4;
        double speed = (Math.random() + Math.random() + 1.0) * 0.15;
        double len = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
        this.xd = this.xd / len * speed * 0.4;
        this.yd = this.yd / len * speed * 0.4 + 0.1;
        this.zd = this.zd / len * speed * 0.4;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd = this.yd - 0.04 * this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
                this.xd *= 1.1;
                this.zd *= 1.1;
            }

            this.xd = this.xd * this.friction;
            this.yd = this.yd * this.friction;
            this.zd = this.zd * this.friction;
            if (this.onGround) {
                this.xd *= 0.7;
                this.zd *= 0.7;
            }
        }
    }

    public void move(double dx, double dy, double dz) {
        if (this.hasPhysics) {
            this.moveEntity(dx, dy, dz);
            this.x = this.posX;
            this.y = this.boundingBox.minY;
            this.z = this.posZ;
        } else {
            this.x += dx;
            this.y += dy;
            this.z += dz;
            this.setPosition(this.x, this.y, this.z);
        }
    }

    public void remove() {
        this.removed = true;
        this.setDead();
    }

    public boolean isAlive() {
        return !this.removed;
    }

    public int getLightColor(float partial) {
        return this.getBrightnessForRender(partial);
    }

    public float getQuadSize(float partial) {
        return this.quadSize;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public Particle scale(float f) {
        this.quadSize *= f;
        return this;
    }

    public Particle setPower(float f) {
        this.xd *= f;
        this.yd = (this.yd - 0.1) * f + 0.1;
        this.zd *= f;
        return this;
    }

    public void setColor(float r, float g, float b) {
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
    }

    public void setAlpha(float a) {
        this.alpha = a;
    }

    public void setLifetime(int t) {
        this.lifetime = t;
    }

    public int getLifetime() {
        return this.lifetime;
    }

    public void setSizeCompat(float w, float h) {
        this.bbWidth = w;
        this.bbHeight = h;
        this.setSize(w, h);
    }

    public void setSprite(IIcon icon) {
        this.sprite = icon;
        this.setParticleIcon(icon);
    }

    public void pickSprite(SpriteSet set) {
        this.setSprite(set.get(this.random));
    }

    public void setSpriteFromAge(SpriteSet set) {
        if (!this.removed) {
            this.setSprite(set.get(this.age, this.lifetime));
        }
    }

    public void onUpdate() {
        this.tick();
        this.prevPosX = this.xo;
        this.prevPosY = this.yo;
        this.prevPosZ = this.zo;
        this.posX = this.x;
        this.posY = this.y;
        this.posZ = this.z;
    }

    public int getFXLayer() {
        return 2;
    }

    public int getBrightnessForRender(float partial) {
        return super.getBrightnessForRender(partial);
    }

    public void renderParticle(Tessellator t, float partial, float rx, float rxz, float rz, float ryz, float rxy) {
        if (this.sprite != null) {
            float u0 = this.sprite.getMinU();
            float u1 = this.sprite.getMaxU();
            float v0 = this.sprite.getMinV();
            float v1 = this.sprite.getMaxV();
            float size = this.getQuadSize(partial);
            float px = (float)(this.xo + (this.x - this.xo) * partial - interpPosX);
            float py = (float)(this.yo + (this.y - this.yo) * partial - interpPosY);
            float pz = (float)(this.zo + (this.z - this.zo) * partial - interpPosZ);
            int light = this.getLightColor(partial);
            t.setBrightness(light);
            t.setColorRGBA_F(this.rCol, this.gCol, this.bCol, this.alpha);
            float r = this.oRoll + (this.roll - this.oRoll) * partial;
            float[][] corners = new float[][]{{-1.0F, -1.0F}, {-1.0F, 1.0F}, {1.0F, 1.0F}, {1.0F, -1.0F}};
            float[][] uv = new float[][]{{u1, v1}, {u1, v0}, {u0, v0}, {u0, v1}};
            float cos = MathHelper.cos(r);
            float sin = MathHelper.sin(r);

            for (int i = 0; i < 4; i++) {
                float cx = corners[i][0] * cos - corners[i][1] * sin;
                float cy = corners[i][0] * sin + corners[i][1] * cos;
                t.addVertexWithUV(px + (-rx * cx - ryz * cy) * size, py + rxz * cy * size, pz + (-rz * cx - rxy * cy) * size, uv[i][0], uv[i][1]);
            }
        }
    }

    public AxisAlignedBB getBoundingBox() {
        return null;
    }
}
