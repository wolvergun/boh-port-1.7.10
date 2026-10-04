package net.mcreator.boh.compat.world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * Natural spawns from the mod's forge:add_spawns biome modifiers (data/boh/forge/biome_modifier), with 1.20 biome
 * ids mapped to the closest 1.7.10 biomes. Spawn conditions still go through the mod's SpawnPlacements rules.
 */
public final class Spawning {

    private static final Map<String, int[]> BIOMES = new HashMap<>();

    static {
        // 1.20 id -> 1.7.10 biome ids (mutated variants are base + 128)
        String[][] m = { { "plains", "1" }, { "sunflower_plains", "129" }, { "desert", "2" }, { "windswept_hills", "3" },
            { "forest", "4" }, { "flower_forest", "132" }, { "taiga", "5" }, { "swamp", "6" }, { "mangrove_swamp", "6" }, { "river", "7" },
            { "nether_wastes", "8" }, { "soul_sand_valley", "8" }, { "crimson_forest", "8" }, { "warped_forest", "8" }, { "basalt_deltas", "8" },
            { "the_end", "9" }, { "frozen_ocean", "10" }, { "frozen_river", "11" }, { "snowy_plains", "12" }, { "ice_spikes", "140" },
            { "mushroom_fields", "14" }, { "beach", "16" }, { "jungle", "21" }, { "bamboo_jungle", "21" }, { "sparse_jungle", "23" },
            { "deep_ocean", "24" }, { "stony_shore", "25" }, { "snowy_beach", "26" }, { "birch_forest", "27" },
            { "old_growth_birch_forest", "155" }, { "dark_forest", "29" }, { "snowy_taiga", "30" }, { "old_growth_pine_taiga", "32" },
            { "old_growth_spruce_taiga", "160" }, { "windswept_forest", "34" }, { "savanna", "35" }, { "savanna_plateau", "36" },
            { "badlands", "37" }, { "wooded_badlands", "38" }, { "eroded_badlands", "165" }, { "ocean", "0" },
            { "meadow", "3", }, { "grove", "30" }, { "snowy_slopes", "30" }, { "jagged_peaks", "3" }, { "frozen_peaks", "3" },
            { "stony_peaks", "3" }, { "cherry_grove", "4" }, { "windswept_savanna", "163" }, { "windswept_gravelly_hills", "131" } };
        for (String[] p : m) BIOMES.put(p[0], new int[] { Integer.parseInt(p[1]) });
    }

    private Spawning() {}

    public static void init() {
        int added = 0;
        try (InputStream in = Spawning.class.getResourceAsStream("/assets/boh/compat/biome_modifiers.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String name;
            while ((name = r.readLine()) != null) {
                name = name.trim();
                if (name.isEmpty()) continue;
                JsonObject o = read("/data/boh/forge/biome_modifier/" + name + ".json");
                if (o == null || !"forge:add_spawns".equals(o.get("type").getAsString())) continue;
                BiomeGenBase[] biomes = biomes(o.get("biomes"));
                if (biomes.length == 0) continue;
                JsonElement sp = o.get("spawners");
                List<JsonObject> spawners = new ArrayList<>();
                if (sp.isJsonArray()) for (JsonElement e : sp.getAsJsonArray()) spawners.add(e.getAsJsonObject());
                else spawners.add(sp.getAsJsonObject());
                for (JsonObject s : spawners) if (add(s, biomes)) added++;
            }
        } catch (Exception e) {
            BohMod.LOGGER.error("Could not read biome modifiers", e);
        }
        BohMod.LOGGER.info("Registered {} natural spawn entries", added);
    }

    @SuppressWarnings("unchecked")
    public static boolean add(JsonObject s, BiomeGenBase[] biomes) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(s.get("type").getAsString()));
        if (type == null || type.getEntityClass() == null || !EntityLiving.class.isAssignableFrom(type.getEntityClass())) return false;
        EnumCreatureType ct = type.getCategory() == null ? null : type.getCategory().toVanilla();
        if (ct == null) ct = EnumCreatureType.creature;
        EntityRegistry.addSpawn((Class<? extends EntityLiving>) type.getEntityClass(), s.get("weight").getAsInt(), s.get("minCount").getAsInt(),
            s.get("maxCount").getAsInt(), ct, biomes);
        return true;
    }

    public static BiomeGenBase[] biomes(JsonElement e) {
        Set<BiomeGenBase> out = new LinkedHashSet<>();
        if (e.isJsonObject()) {
            // forge:any
            for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray()) if (b != null && b.biomeID != 8 && b.biomeID != 9) out.add(b);
        } else if (e.isJsonArray()) {
            for (JsonElement x : e.getAsJsonArray()) addBiome(x.getAsString(), out);
        } else {
            addBiome(e.getAsString(), out);
        }
        return out.toArray(new BiomeGenBase[0]);
    }

    private static void addBiome(String id, Set<BiomeGenBase> out) {
        if (id.startsWith("#")) return;
        if (id.contains(":") && !id.startsWith("minecraft:")) {
            BiomeGenBase b = Biomes.byKey(new ResourceLocation(id));
            if (b != null) out.add(b);
            return;
        }
        int[] ids = BIOMES.get(id.replace("minecraft:", ""));
        if (ids == null) return;
        for (int i : ids) {
            BiomeGenBase b = i >= 0 && i < BiomeGenBase.getBiomeGenArray().length ? BiomeGenBase.getBiomeGenArray()[i] : null;
            if (b != null) out.add(b);
        }
    }

    private static JsonObject read(String path) {
        try (InputStream in = Spawning.class.getResourceAsStream(path)) {
            return in == null ? null : new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    static JsonArray unused() {
        return null;
    }
}
