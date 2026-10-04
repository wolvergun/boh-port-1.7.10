package net.mcreator.boh.compat.world.gen;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** One provider class for all of the mod's dimensions; the per-dimension settings come from {@link Spec}. */
public class BohWorldProvider extends WorldProvider {

    public static final class Spec {

        public final ResourceLocation name;
        public final int id;
        public final BohBiome biome;
        /** block name for a one-block floor at y=0, or null for a void world */
        public final String floor;
        public final boolean skylight, bedWorks;
        public final float ambientLight;

        public Spec(ResourceLocation name, int id, BohBiome biome, String floor, boolean skylight, boolean bedWorks, float ambientLight) {
            this.name = name;
            this.id = id;
            this.biome = biome;
            this.floor = floor;
            this.skylight = skylight;
            this.bedWorks = bedWorks;
            this.ambientLight = ambientLight;
        }
    }

    public static final Map<Integer, Spec> SPECS = new HashMap<>();

    public Spec spec() {
        return SPECS.get(dimensionId);
    }

    /** The generator's base surface height (what 1.20 jigsaw structures project onto). */
    public static int baseHeight(World w, int x, int z) {
        if (w.provider instanceof BohWorldProvider) return ((BohWorldProvider) w.provider).spec().floor != null ? 1 : 0;
        return Math.max(0, w.getHeightValue(x, z));
    }

    @Override
    protected void registerWorldChunkManager() {
        Spec s = spec();
        worldChunkMgr = new WorldChunkManagerHell(s.biome, s.biome.temperature);
        hasNoSky = !s.skylight;
    }

    @Override
    protected void generateLightBrightnessTable() {
        Spec s = spec();
        float amb = s == null ? 0 : s.ambientLight;
        for (int i = 0; i <= 15; i++) {
            float f = i / 15.0F;
            float v = f / (4.0F - 3.0F * f);
            lightBrightnessTable[i] = v + (1.0F - v) * amb;
        }
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new BohChunkProvider(worldObj, spec());
    }

    @Override
    public String getDimensionName() {
        Spec s = spec();
        return s == null ? "boh" : s.name.toString();
    }

    @Override
    public boolean isSurfaceWorld() {
        // 1.20 effects use SkyType.NONE: no sun, moon, stars or clouds
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return spec().bedWorks;
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return true;
    }

    @Override
    public int getAverageGroundLevel() {
        return spec().floor != null ? 1 : 64;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getHorizon() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return -1000.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float celestialAngle, float partialTicks) {
        int c = spec().biome.fogColor;
        float sun = MathHelper.clamp_float(MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
        net.mcreator.boh.compat.mc.world.phys.Vec3 v = new net.mcreator.boh.compat.mc.world.phys.Vec3((c >> 16 & 255) / 255.0, (c >> 8 & 255) / 255.0,
            (c & 255) / 255.0);
        net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects fx = net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager
            .getForType(spec().name);
        if (fx != null) v = fx.getBrightnessDependentFogColor(v, sun);
        return Vec3.createVectorHelper(v.x, v.y, v.z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects fx = net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager
            .getForType(spec().name);
        return fx != null && fx.isFoggyAt(x, z);
    }

    /** Registers the provider type and dimension under the mod's dimension key. */
    public static void register(Spec s) {
        SPECS.put(s.id, s);
        net.minecraftforge.common.DimensionManager.registerProviderType(s.id, BohWorldProvider.class, false);
        net.minecraftforge.common.DimensionManager.registerDimension(s.id, s.id);
        Dimensions.register(s.name, s.id);
    }
}
