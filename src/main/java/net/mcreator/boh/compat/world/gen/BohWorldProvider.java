package net.mcreator.boh.compat.world.gen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;

public class BohWorldProvider extends WorldProvider {
    public static final Map<Integer, BohWorldProvider.Spec> SPECS = new HashMap<>();

    public BohWorldProvider.Spec spec() {
        return SPECS.get(this.dimensionId);
    }

    public static int baseHeight(World w, int x, int z) {
        if (w.provider instanceof BohWorldProvider) {
            return ((BohWorldProvider)w.provider).spec().floor != null ? 1 : 0;
        } else {
            return Math.max(0, w.getHeightValue(x, z));
        }
    }

    protected void registerWorldChunkManager() {
        BohWorldProvider.Spec s = this.spec();
        this.worldChunkMgr = new WorldChunkManagerHell(s.biome, s.biome.temperature);
        this.hasNoSky = !s.skylight;
    }

    protected void generateLightBrightnessTable() {
        BohWorldProvider.Spec s = this.spec();
        float amb = s == null ? 0.0F : s.ambientLight;

        for (int i = 0; i <= 15; i++) {
            float f = i / 15.0F;
            float v = f / (4.0F - 3.0F * f);
            this.lightBrightnessTable[i] = v + (1.0F - v) * amb;
        }
    }

    public IChunkProvider createChunkGenerator() {
        return new BohChunkProvider(this.worldObj, this.spec());
    }

    public String getDimensionName() {
        BohWorldProvider.Spec s = this.spec();
        return s == null ? "boh" : s.name.toString();
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    public boolean canRespawnHere() {
        return this.spec().bedWorks;
    }

    public boolean canCoordinateBeSpawn(int x, int z) {
        return true;
    }

    public int getAverageGroundLevel() {
        return this.spec().floor != null ? 1 : 64;
    }

    @SideOnly(Side.CLIENT)
    public double getHorizon() {
        return 0.0;
    }

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return -1000.0F;
    }

    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float celestialAngle, float partialTicks) {
        int c = this.spec().biome.fogColor;
        float sun = MathHelper.clamp_float(MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
        net.mcreator.boh.compat.mc.world.phys.Vec3 v = new net.mcreator.boh.compat.mc.world.phys.Vec3(
            (c >> 16 & 0xFF) / 255.0, (c >> 8 & 0xFF) / 255.0, (c & 0xFF) / 255.0
        );
        DimensionSpecialEffects fx = DimensionSpecialEffectsManager.getForType(this.spec().name);
        if (fx != null) {
            v = fx.getBrightnessDependentFogColor(v, sun);
        }

        return Vec3.createVectorHelper(v.x, v.y, v.z);
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        DimensionSpecialEffects fx = DimensionSpecialEffectsManager.getForType(this.spec().name);
        return fx != null && fx.isFoggyAt(x, z);
    }

    public static void register(BohWorldProvider.Spec s) {
        SPECS.put(s.id, s);
        DimensionManager.registerProviderType(s.id, BohWorldProvider.class, false);
        DimensionManager.registerDimension(s.id, s.id);
        Dimensions.register(s.name, s.id);
    }

    public static final class Spec {
        public final ResourceLocation name;
        public final int id;
        public final BohBiome biome;
        public final String floor;
        public final boolean skylight;
        public final boolean bedWorks;
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
}
