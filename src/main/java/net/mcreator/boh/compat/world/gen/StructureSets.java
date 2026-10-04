package net.mcreator.boh.compat.world.gen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public final class StructureSets {
    private static final List<StructureSets.Entry> SETS = new ArrayList<>();
    private static final Set<String> LOGGED = Collections.synchronizedSet(new HashSet<>());

    public static int count() {
        return SETS.size();
    }

    private StructureSets() {
    }

    public static void init() {
        String name;
        try (
            InputStream in = StructureSets.class.getResourceAsStream("/assets/boh/compat/structure_sets.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        ) {
            while ((name = r.readLine()) != null) {
                name = name.trim();
                if (!name.isEmpty()) {
                    JsonObject set = WorldgenData.get("worldgen/structure_set", new ResourceLocation("boh", name));
                    if (set != null) {
                        JsonObject pl = set.getAsJsonObject("placement");
                        if (pl.get("type").getAsString().endsWith("random_spread")) {
                            for (JsonElement se : set.getAsJsonArray("structures")) {
                                String sid = se.getAsJsonObject().get("structure").getAsString();
                                JsonObject st = WorldgenData.get("worldgen/structure", new ResourceLocation(sid));
                                if (st != null && st.has("start_pool")) {
                                    JsonObject pool = WorldgenData.get("worldgen/template_pool", new ResourceLocation(st.get("start_pool").getAsString()));
                                    if (pool != null) {
                                        String loc = pool.getAsJsonArray("elements")
                                            .get(0)
                                            .getAsJsonObject()
                                            .getAsJsonObject("element")
                                            .get("location")
                                            .getAsString();
                                        SETS.add(
                                            new StructureSets.Entry(
                                                sid,
                                                loc,
                                                st.has("project_start_to_heightmap") ? st.get("project_start_to_heightmap").getAsString() : null,
                                                st.get("biomes").getAsString(),
                                                pl.get("spacing").getAsInt(),
                                                pl.get("separation").getAsInt(),
                                                pl.get("salt").getAsInt(),
                                                st.has("start_height") ? WorldgenData.yValue(st.getAsJsonObject("start_height")) : 0
                                            )
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception var15) {
            BohMod.LOGGER.error("Could not read structure sets", var15);
        }
    }

    public static void generate(World w, int cx, int cz, ResourceLocation biome) {
        if (biome != null) {
            for (StructureSets.Entry e : SETS) {
                if (e.biomes.equals(biome.toString())) {
                    int sx = Math.floorDiv(cx, e.spacing);
                    int sz = Math.floorDiv(cz, e.spacing);
                    Random r = new Random(sx * 341873128712L + sz * 132897987541L + w.getSeed() + e.salt);
                    int range = Math.max(1, e.spacing - e.separation);
                    if (sx * e.spacing + r.nextInt(range) == cx && sz * e.spacing + r.nextInt(range) == cz) {
                        StructureTemplate t = StructureTemplateManager.INSTANCE.getOrCreate(new ResourceLocation(e.pool));
                        int x = cx * 16 + 16;
                        int z = cz * 16 + 16;
                        int y = e.heightmap == null ? e.startY + 64 : BohWorldProvider.baseHeight(w, x, z) + e.startY - 1;
                        Random pr = new Random(w.getSeed() ^ (cx * 31L + cz) * -7046029254386353131L ^ e.salt);
                        StructurePlaceSettings s = new StructurePlaceSettings()
                            .setRotation(Rotation.values()[pr.nextInt(4)])
                            .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

                        try {
                            boolean ok = t.placeInWorld(w, new BlockPos(x, y, z), new BlockPos(0, 0, 0), s, pr, 2);
                            if (LOGGED.add(e.structure)) {
                                BohMod.LOGGER
                                    .info(
                                        "Structure {} ({}) at {} {} {}: {}",
                                        new Object[]{e.structure, e.pool, x, y, z, ok ? "placed" : "template empty or not placed"}
                                    );
                            }
                        } catch (Throwable var17) {
                            if (LOGGED.add(e.structure + "!")) {
                                BohMod.LOGGER.error("Structure " + e.structure + " failed", var17);
                            }
                        }
                    }
                }
            }
        }
    }

    static final class Entry {
        final String structure;
        final String pool;
        final String heightmap;
        final String biomes;
        final int spacing;
        final int separation;
        final int salt;
        final int startY;

        Entry(String structure, String pool, String heightmap, String biomes, int spacing, int separation, int salt, int startY) {
            this.structure = structure;
            this.pool = pool;
            this.heightmap = heightmap;
            this.biomes = biomes;
            this.spacing = spacing;
            this.separation = separation;
            this.salt = salt;
            this.startY = startY;
        }
    }
}
