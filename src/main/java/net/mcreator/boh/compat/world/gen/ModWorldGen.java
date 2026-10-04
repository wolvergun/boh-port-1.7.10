package net.mcreator.boh.compat.world.gen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.common.IWorldGenerator;
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
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.world.Biomes;
import net.mcreator.boh.compat.world.Spawning;
import net.minecraft.block.BlockFalling;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public final class ModWorldGen implements IWorldGenerator {
    public static final ModWorldGen INSTANCE = new ModWorldGen();
    private static final Map<Integer, List<ResourceLocation>> BY_BIOME = new HashMap<>();
    private static boolean loggedFirst;

    private ModWorldGen() {
    }

    public static void init() {
        int n = 0;

        String name;
        try (
            InputStream in = ModWorldGen.class.getResourceAsStream("/assets/boh/compat/biome_modifiers.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        ) {
            while ((name = r.readLine()) != null) {
                name = name.trim();
                if (!name.isEmpty()) {
                    JsonObject o = WorldgenData.read("/data/boh/forge/biome_modifier/" + name + ".json");
                    if (o != null && "forge:add_features".equals(o.get("type").getAsString())) {
                        List<ResourceLocation> feats = new ArrayList<>();
                        JsonElement f = o.get("features");
                        if (f.isJsonArray()) {
                            for (JsonElement e : f.getAsJsonArray()) {
                                feats.add(new ResourceLocation(e.getAsString()));
                            }
                        } else {
                            feats.add(new ResourceLocation(f.getAsString()));
                        }

                        for (BiomeGenBase b : Spawning.biomes(o.get("biomes"))) {
                            BY_BIOME.computeIfAbsent(b.biomeID, k -> new ArrayList<>()).addAll(feats);
                            n++;
                        }
                    }
                }
            }
        } catch (Exception var15) {
            BohMod.LOGGER.error("Could not read feature modifiers", var15);
        }

        StructureSets.init();
        BohMod.LOGGER.info("World generation: {} biome feature entries", new Object[]{n});
    }

    public void generate(Random random, int cx, int cz, World world, IChunkProvider gen, IChunkProvider provider) {
        if (!(world.provider instanceof BohWorldProvider)) {
            this.populate(random, cx, cz, world);
        }
    }

    public void populate(Random random, int cx, int cz, World world) {
        this.populate(random, cx, cz, world, world.getBiomeGenForCoords(cx * 16 + 16, cz * 16 + 16));
    }

    public void populate(Random random, int cx, int cz, World world, BiomeGenBase biome) {
        if (world.provider instanceof BohWorldProvider && !loggedFirst) {
            loggedFirst = true;
            BohMod.LOGGER
                .info(
                    "Populating {} chunk {},{}: biome {} ({}), {} structure sets loaded",
                    new Object[]{
                        world.provider.getDimensionName(),
                        cx,
                        cz,
                        biome == null ? null : biome.biomeName,
                        biome == null ? null : Biomes.keyOf(biome),
                        StructureSets.count()
                    }
                );
        }

        if (biome != null) {
            boolean fall = BlockFalling.fallInstantly;
            BlockFalling.fallInstantly = true;

            try {
                ResourceLocation key = Biomes.keyOf(biome);
                if (key != null) {
                    StructureSets.generate(world, cx, cz, key);
                }

                Set<ResourceLocation> feats = new LinkedHashSet<>();
                if (biome instanceof BohBiome) {
                    feats.addAll(((BohBiome)biome).features);
                }

                List<ResourceLocation> l = BY_BIOME.get(biome.biomeID);
                if (l != null) {
                    feats.addAll(l);
                }

                for (ResourceLocation f : feats) {
                    try {
                        Features.place(f, world, random, cx, cz);
                    } catch (Exception var16) {
                        BohMod.LOGGER.debug("feature " + f + " failed", var16);
                    }
                }
            } finally {
                BlockFalling.fallInstantly = fall;
            }
        }
    }
}
