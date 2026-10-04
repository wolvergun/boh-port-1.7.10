package net.mcreator.boh.compat.mc.client.particle;

import java.util.Random;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

/**
 * Port of the 1.20 client Particle on top of {@link EntityFX}: modern field names and tick/render logic, drawn on
 * the items-atlas layer.
 */
public class Particle extends EntityFX {

    public double x, y, z, xo, yo, zo, xd, yd, zd;
    public int age, lifetime;
    public float gravity, friction = 0.98F, quadSize;
    public float rCol = 1, gCol = 1, bCol = 1, alpha = 1;
    public float roll, oRoll;
    public boolean hasPhysics = true, removed, speedUpWhenYMotionIsBlocked;
    public final Random random = new Random();
    protected IIcon sprite;
    protected float bbWidth = 0.6F, bbHeight = 1.8F;

    protected Particle(WorldClient world, double x, double y, double z) {
        super(world, x, y, z, 0, 0, 0);
        this.x = this.xo = x;
        this.y = this.yo = y;
        this.z = this.zo = z;
        quadSize = 0.1F * (random.nextFloat() * 0.5F + 0.5F) * 2.0F;
        lifetime = (int) (4.0F / (random.nextFloat() * 0.9F + 0.1F));
        motionX = motionY = motionZ = 0;
    }

    protected Particle(WorldClient world, double x, double y, double z, double dx, double dy, double dz) {
        this(world, x, y, z);
        xd = dx + (Math.random() * 2.0 - 1.0) * 0.4;
        yd = dy + (Math.random() * 2.0 - 1.0) * 0.4;
        zd = dz + (Math.random() * 2.0 - 1.0) * 0.4;
        double speed = (Math.random() + Math.random() + 1.0) * 0.15;
        double len = Math.sqrt(xd * xd + yd * yd + zd * zd);
        xd = xd / len * speed * 0.4;
        yd = yd / len * speed * 0.4 + 0.1;
        zd = zd / len * speed * 0.4;
    }

    // ------------------------------------------------------------------ 1.20 API

    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        if (age++ >= lifetime) {
            remove();
            return;
        }
        yd -= 0.04 * gravity;
        move(xd, yd, zd);
        if (speedUpWhenYMotionIsBlocked && y == yo) {
            xd *= 1.1;
            zd *= 1.1;
        }
        xd *= friction;
        yd *= friction;
        zd *= friction;
        if (onGround) {
            xd *= 0.7;
            zd *= 0.7;
        }
    }

    public void move(double dx, double dy, double dz) {
        if (hasPhysics) {
            moveEntity(dx, dy, dz);
            x = posX;
            y = boundingBox.minY;
            z = posZ;
        } else {
            x += dx;
            y += dy;
            z += dz;
            setPosition(x, y, z);
        }
    }

    public void remove() {
        removed = true;
        setDead();
    }

    public boolean isAlive() {
        return !removed;
    }

    public int getLightColor(float partial) {
        return getBrightnessForRender(partial);
    }

    public float getQuadSize(float partial) {
        return quadSize;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public Particle scale(float f) {
        quadSize *= f;
        return this;
    }

    public Particle setPower(float f) {
        xd *= f;
        yd = (yd - 0.1) * f + 0.1;
        zd *= f;
        return this;
    }

    public void setColor(float r, float g, float b) {
        rCol = r;
        gCol = g;
        bCol = b;
    }

    public void setAlpha(float a) {
        alpha = a;
    }

    public void setLifetime(int t) {
        lifetime = t;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setSizeCompat(float w, float h) {
        bbWidth = w;
        bbHeight = h;
        setSize(w, h);
    }

    public void setSprite(IIcon icon) {
        sprite = icon;
        setParticleIcon(icon);
    }

    public void pickSprite(SpriteSet set) {
        setSprite(set.get(random));
    }

    public void setSpriteFromAge(SpriteSet set) {
        if (!removed) setSprite(set.get(age, lifetime));
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    @Override
    public void onUpdate() {
        tick();
        prevPosX = xo;
        prevPosY = yo;
        prevPosZ = zo;
        posX = x;
        posY = y;
        posZ = z;
    }

    @Override
    public int getFXLayer() {
        return 2;
    }

    @Override
    public int getBrightnessForRender(float partial) {
        return super.getBrightnessForRender(partial);
    }

    @Override
    public void renderParticle(Tessellator t, float partial, float rx, float rxz, float rz, float ryz, float rxy) {
        if (sprite == null) return;
        float u0 = sprite.getMinU(), u1 = sprite.getMaxU(), v0 = sprite.getMinV(), v1 = sprite.getMaxV();
        float size = getQuadSize(partial);
        float px = (float) (xo + (x - xo) * partial - interpPosX);
        float py = (float) (yo + (y - yo) * partial - interpPosY);
        float pz = (float) (zo + (z - zo) * partial - interpPosZ);
        int light = getLightColor(partial);
        t.setBrightness(light);
        t.setColorRGBA_F(rCol, gCol, bCol, alpha);
        float r = oRoll + (roll - oRoll) * partial;
        float[][] corners = { { -1, -1 }, { -1, 1 }, { 1, 1 }, { 1, -1 } };
        float[][] uv = { { u1, v1 }, { u1, v0 }, { u0, v0 }, { u0, v1 } };
        float cos = MathHelper.cos(r), sin = MathHelper.sin(r);
        for (int i = 0; i < 4; i++) {
            float cx = corners[i][0] * cos - corners[i][1] * sin, cy = corners[i][0] * sin + corners[i][1] * cos;
            t.addVertexWithUV(px + (-rx * cx - ryz * cy) * size, py + rxz * cy * size, pz + (-rz * cx - rxy * cy) * size, uv[i][0], uv[i][1]);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
}
