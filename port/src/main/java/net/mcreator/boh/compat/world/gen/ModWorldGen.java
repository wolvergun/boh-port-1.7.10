package net.mcreator.boh.compat.world.gen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.mcreator.boh.compat.world.Biomes;
import net.mcreator.boh.compat.world.Spawning;
import net.minecraft.block.BlockFalling;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

/** Places the mod's forge:add_features biome modifiers, mod biome features and structure sets during population. */
public final class ModWorldGen implements IWorldGenerator {

    public static final ModWorldGen INSTANCE = new ModWorldGen();

    /** biome id -> placed features from biome modifiers */
    private static final Map<Integer, List<ResourceLocation>> BY_BIOME = new HashMap<>();

    private ModWorldGen() {}

    /** placed feature -> biomes it was added to by the biome modifiers (for the self-test) */
    public static Map<ResourceLocation, List<BiomeGenBase>> featureBiomes() {
        Map<ResourceLocation, List<BiomeGenBase>> out = new java.util.LinkedHashMap<>();
        for (Map.Entry<Integer, List<ResourceLocation>> e : BY_BIOME.entrySet())
            for (ResourceLocation f : e.getValue()) out.computeIfAbsent(f, k -> new ArrayList<>()).add(BiomeGenBase.getBiomeGenArray()[e.getKey()]);
        return out;
    }

    public static void init() {
        int n = 0;
        try (InputStream in = ModWorldGen.class.getResourceAsStream("/assets/boh/compat/biome_modifiers.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String name;
            while ((name = r.readLine()) != null) {
                name = name.trim();
                if (name.isEmpty()) continue;
                JsonObject o = WorldgenData.read("/data/boh/forge/biome_modifier/" + name + ".json");
                if (o == null || !"forge:add_features".equals(o.get("type").getAsString())) continue;
                List<ResourceLocation> feats = new ArrayList<>();
                JsonElement f = o.get("features");
                if (f.isJsonArray()) for (JsonElement e : f.getAsJsonArray()) feats.add(new ResourceLocation(e.getAsString()));
                else feats.add(new ResourceLocation(f.getAsString()));
                for (BiomeGenBase b : Spawning.biomes(o.get("biomes"))) {
                    BY_BIOME.computeIfAbsent(b.biomeID, k -> new ArrayList<>()).addAll(feats);
                    n++;
                }
            }
        } catch (Exception e) {
            net.mcreator.boh.BohMod.LOGGER.error("Could not read feature modifiers", e);
        }
        StructureSets.init();
        net.mcreator.boh.BohMod.LOGGER.info("World generation: {} biome feature entries", n);
    }

    @Override
    public void generate(Random random, int cx, int cz, World world, IChunkProvider gen, IChunkProvider provider) {
        // the mod's own dimensions call populate() from their chunk provider
        if (world.provider instanceof BohWorldProvider) return;
        populate(random, cx, cz, world);
    }

    public void populate(Random random, int cx, int cz, World world) {
        populate(random, cx, cz, world, world.getBiomeGenForCoords(cx * 16 + 16, cz * 16 + 16));
    }

    private static boolean loggedFirst;
    private static final Set<ResourceLocation> FAILED = java.util.Collections.synchronizedSet(new java.util.HashSet<>());

    public void populate(Random random, int cx, int cz, World world, BiomeGenBase biome) {
        if (world.provider instanceof BohWorldProvider && !loggedFirst) {
            loggedFirst = true;
            net.mcreator.boh.BohMod.LOGGER.info("Populating {} chunk {},{}: biome {} ({}), {} structure sets loaded", world.provider.getDimensionName(), cx, cz,
                biome == null ? null : biome.biomeName, biome == null ? null : Biomes.keyOf(biome), StructureSets.count());
        }
        if (biome == null) return;
        boolean fall = BlockFalling.fallInstantly;
        BlockFalling.fallInstantly = true;
        try {
            ResourceLocation key = Biomes.keyOf(biome);
            if (key != null) StructureSets.generate(world, cx, cz, key);
            Set<ResourceLocation> feats = new LinkedHashSet<>();
            if (biome instanceof BohBiome) feats.addAll(((BohBiome) biome).features);
            List<ResourceLocation> l = BY_BIOME.get(biome.biomeID);
            if (l != null) feats.addAll(l);
            for (ResourceLocation f : feats) {
                try {
                    Features.place(f, world, random, cx, cz);
                } catch (Exception e) {
                    if (FAILED.add(f)) net.mcreator.boh.BohMod.LOGGER.warn("feature " + f + " failed", e);
                }
            }
        } finally {
            BlockFalling.fallInstantly = fall;
        }
    }
}
