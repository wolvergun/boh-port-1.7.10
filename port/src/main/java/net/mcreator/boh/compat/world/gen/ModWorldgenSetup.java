package net.mcreator.boh.compat.world.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.world.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;

/** Creates the mod's biomes and dimensions (ids from config/boh.cfg) and hooks world generation. */
public final class ModWorldgenSetup {

    private static final String[] BIOMES = { "baseplate", "boiler_room_biome", "gaster_biome", "level_0_biome", "shrouded_cliffs" };
    private static final List<BohBiome> CREATED = new ArrayList<>();

    private ModWorldgenSetup() {}

    public static void preInit(File configFile) {
        Configuration cfg = new Configuration(configFile);
        int nextBiome = 180;
        for (String b : BIOMES) {
            int id = cfg.get("biomes", b, nextBiome++).getInt();
            if (id < 0 || id >= BiomeGenBase.getBiomeGenArray().length || BiomeGenBase.getBiomeGenArray()[id] != null) {
                int free = freeBiomeId();
                BohMod.LOGGER.warn("Biome id {} for boh:{} is taken, using {}", id, b, free);
                id = free;
                cfg.get("biomes", b, id).set(id);
            }
            if (id < 0) continue;
            JsonObject json = WorldgenData.read("/data/boh/worldgen/biome/" + b + ".json");
            if (json == null) continue;
            BohBiome biome = new BohBiome(id, new ResourceLocation("boh", b), json);
            Biomes.register(biome, biome.key);
            CREATED.add(biome);
            if (b.equals("shrouded_cliffs")) {
                biome.setHeight(new BiomeGenBase.Height(0.9F, 0.9F));
                biome.theBiomeDecorator.grassPerChunk = 7;
                biome.theBiomeDecorator.flowersPerChunk = 2;
                biome.theBiomeDecorator.mushroomsPerChunk = 1;
                if (cfg.get("biomes", "shrouded_cliffs_in_overworld", true).getBoolean(true)) {
                    BiomeManager.addBiome(BiomeManager.BiomeType.COOL,
                        new BiomeManager.BiomeEntry(biome, cfg.get("biomes", "shrouded_cliffs_weight", 10).getInt()));
                    BiomeManager.addSpawnBiome(biome);
                }
                BiomeDictionary.registerBiomeType(biome, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS,
                    BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD);
            }
        }
        dim(cfg, "level_0", 340, "level_0_biome", "boh:backrooms_floor", true, false, 0.0F);
        dim(cfg, "baseplate_dimension", 341, "baseplate", null, false, false, 0.5F);
        dim(cfg, "boiler_room_dimension", 342, "boiler_room_biome", null, true, true, 0.0F);
        dim(cfg, "gaster_dimension", 343, "gaster_biome", null, false, false, 0.5F);
        if (cfg.hasChanged()) cfg.save();
    }

    private static void dim(Configuration cfg, String name, int def, String biome, String floor, boolean skylight, boolean bed, float ambient) {
        int id = cfg.get("dimensions", name, def).getInt();
        if (DimensionManager.isDimensionRegistered(id)) {
            BohMod.LOGGER.error("Dimension id {} for boh:{} is already used by another mod; change it in config/boh.cfg", id, name);
            return;
        }
        BohBiome b = null;
        for (BohBiome x : CREATED) if (x.key.getResourcePath().equals(biome)) b = x;
        if (b == null) return;
        BohWorldProvider.register(new BohWorldProvider.Spec(new ResourceLocation("boh", name), id, b, floor, skylight, bed, ambient));
    }

    private static int freeBiomeId() {
        BiomeGenBase[] arr = BiomeGenBase.getBiomeGenArray();
        for (int i = Math.min(arr.length, 256) - 1; i > 40; i--) if (arr[i] == null) return i;
        for (int i = arr.length - 1; i > 40; i--) if (arr[i] == null) return i;
        return -1;
    }

    public static void postInit() {
        for (BohBiome b : CREATED) b.addSpawners();
        ModWorldGen.init();
        GameRegistry.registerWorldGenerator(ModWorldGen.INSTANCE, 5);
    }
}
