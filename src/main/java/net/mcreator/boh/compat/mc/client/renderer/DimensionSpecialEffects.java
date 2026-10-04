package net.mcreator.boh.compat.mc.client.renderer;

import net.mcreator.boh.compat.client.LightTexture;
import net.mcreator.boh.compat.client.Matrix4f;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mojang.math.Vector3f;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MathHelper;

public abstract class DimensionSpecialEffects {
    private final float cloudLevel;
    private final boolean hasGround;
    private final SkyType skyType;
    private final boolean forceBrightLightmap;
    private final boolean constantAmbientLight;
    private final float[] sunriseCol = new float[4];
    public static final DimensionSpecialEffects OVERWORLD = new DimensionSpecialEffects(192.0F, true, SkyType.NORMAL, false, false) {
        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 c, float h) {
            return new Vec3(c.x * (h * 0.94F + 0.06F), c.y * (h * 0.94F + 0.06F), c.z * (h * 0.91F + 0.09F));
        }

        @Override
        public boolean isFoggyAt(int x, int y) {
            return false;
        }
    };

    public DimensionSpecialEffects(float cloudLevel, boolean hasGround, SkyType skyType, boolean forceBrightLightmap, boolean constantAmbientLight) {
        this.cloudLevel = cloudLevel;
        this.hasGround = hasGround;
        this.skyType = skyType;
        this.forceBrightLightmap = forceBrightLightmap;
        this.constantAmbientLight = constantAmbientLight;
    }

    public float[] getSunriseColor(float timeOfDay, float partialTick) {
        float c = MathHelper.cos(timeOfDay * (float) Math.PI * 2.0F) - 0.0F;
        if (c >= -0.4F && c <= 0.4F) {
            float f3 = c / 0.4F * 0.5F + 0.5F;
            float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float) Math.PI)) * 0.99F;
            f4 *= f4;
            this.sunriseCol[0] = f3 * 0.3F + 0.7F;
            this.sunriseCol[1] = f3 * f3 * 0.7F + 0.2F;
            this.sunriseCol[2] = f3 * f3 * 0.0F + 0.2F;
            this.sunriseCol[3] = f4;
            return this.sunriseCol;
        } else {
            return null;
        }
    }

    public float getCloudHeight() {
        return this.cloudLevel;
    }

    public boolean hasGround() {
        return this.hasGround;
    }

    public SkyType skyType() {
        return this.skyType;
    }

    public boolean forceBrightLightmap() {
        return this.forceBrightLightmap;
    }

    public boolean constantAmbientLight() {
        return this.constantAmbientLight;
    }

    public abstract Vec3 getBrightnessDependentFogColor(Vec3 var1, float var2);

    public abstract boolean isFoggyAt(int var1, int var2);

    public boolean renderClouds(
        WorldClient level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projection
    ) {
        return false;
    }

    public boolean renderSky(
        WorldClient level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projection, boolean isFoggy, Runnable setupFog
    ) {
        return false;
    }

    public boolean renderSnowAndRain(WorldClient level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return false;
    }

    public boolean tickRain(WorldClient level, int ticks, Camera camera) {
        return false;
    }

    public void adjustLightmapColors(
        WorldClient level, float partialTick, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, Vector3f colors
    ) {
    }
}
