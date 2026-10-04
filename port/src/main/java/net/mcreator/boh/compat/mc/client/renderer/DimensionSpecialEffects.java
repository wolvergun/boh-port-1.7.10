package net.mcreator.boh.compat.mc.client.renderer;

import net.mcreator.boh.compat.client.Matrix4f;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mojang.math.Vector3f;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/** 1.20 DimensionSpecialEffects; invoked from the 1.7.10 sky render hook in DimensionEffectsHooks. */
public abstract class DimensionSpecialEffects {

    private final float cloudLevel;
    private final boolean hasGround;
    private final SkyType skyType;
    private final boolean forceBrightLightmap;
    private final boolean constantAmbientLight;
    private final float[] sunriseCol = new float[4];

    public DimensionSpecialEffects(float cloudLevel, boolean hasGround, SkyType skyType, boolean forceBrightLightmap,
        boolean constantAmbientLight) {
        this.cloudLevel = cloudLevel;
        this.hasGround = hasGround;
        this.skyType = skyType;
        this.forceBrightLightmap = forceBrightLightmap;
        this.constantAmbientLight = constantAmbientLight;
    }

    public float[] getSunriseColor(float timeOfDay, float partialTick) {
        float c = MathHelper.cos(timeOfDay * (float) Math.PI * 2) - 0.0F;
        if (c >= -0.4F && c <= 0.4F) {
            float f3 = c / 0.4F * 0.5F + 0.5F;
            float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float) Math.PI)) * 0.99F;
            f4 *= f4;
            sunriseCol[0] = f3 * 0.3F + 0.7F;
            sunriseCol[1] = f3 * f3 * 0.7F + 0.2F;
            sunriseCol[2] = f3 * f3 * 0.0F + 0.2F;
            sunriseCol[3] = f4;
            return sunriseCol;
        }
        return null;
    }

    public float getCloudHeight() {
        return cloudLevel;
    }

    public boolean hasGround() {
        return hasGround;
    }

    public SkyType skyType() {
        return skyType;
    }

    public boolean forceBrightLightmap() {
        return forceBrightLightmap;
    }

    public boolean constantAmbientLight() {
        return constantAmbientLight;
    }

    public abstract Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight);

    public abstract boolean isFoggyAt(int x, int y);

    public boolean renderClouds(net.minecraft.client.multiplayer.WorldClient level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY,
        double camZ, Matrix4f projection) {
        return false;
    }

    public boolean renderSky(net.minecraft.client.multiplayer.WorldClient level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projection,
        boolean isFoggy, Runnable setupFog) {
        return false;
    }

    public boolean renderSnowAndRain(net.minecraft.client.multiplayer.WorldClient level, int ticks, float partialTick, net.mcreator.boh.compat.client.LightTexture lightTexture, double camX, double camY,
        double camZ) {
        return false;
    }

    public boolean tickRain(net.minecraft.client.multiplayer.WorldClient level, int ticks, Camera camera) {
        return false;
    }

    public void adjustLightmapColors(net.minecraft.client.multiplayer.WorldClient level, float partialTick, float skyDarken, float blockLightRedFlicker, float skyLight,
        int pixelX, int pixelY, Vector3f colors) {}

    /** Default overworld effects used for dimensions without registered effects. */
    public static final DimensionSpecialEffects OVERWORLD = new DimensionSpecialEffects(192, true, SkyType.NORMAL, false, false) {

        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 c, float h) {
            return new Vec3(c.x * (h * 0.94F + 0.06F), c.y * (h * 0.94F + 0.06F), c.z * (h * 0.91F + 0.09F));
        }

        @Override
        public boolean isFoggyAt(int x, int y) {
            return false;
        }
    };
}
