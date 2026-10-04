package net.mcreator.boh.compat.world.gen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * The mod's worldgen structure sets (single-piece jigsaw structures with random_spread placement), placed when
 * the start chunk is populated in a biome the structure allows.
 */
public final class StructureSets {

    static final class Entry {

        final String structure, pool, heightmap, biomes;
        final int spacing, separation, salt, startY;

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

    private static final List<Entry> SETS = new ArrayList<>();
    private static final java.util.Set<String> LOGGED = java.util.Collections.synchronizedSet(new java.util.HashSet<>());

    public static int count() {
        return SETS.size();
    }

    private StructureSets() {}

    public static void init() {
        try (InputStream in = StructureSets.class.getResourceAsStream("/assets/boh/compat/structure_sets.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String name;
            while ((name = r.readLine()) != null) {
                name = name.trim();
                if (name.isEmpty()) continue;
                JsonObject set = WorldgenData.get("worldgen/structure_set", new ResourceLocation("boh", name));
                if (set == null) continue;
                JsonObject pl = set.getAsJsonObject("placement");
                if (!pl.get("type").getAsString().endsWith("random_spread")) continue;
                for (JsonElement se : set.getAsJsonArray("structures")) {
                    String sid = se.getAsJsonObject().get("structure").getAsString();
                    JsonObject st = WorldgenData.get("worldgen/structure", new ResourceLocation(sid));
                    if (st == null || !st.has("start_pool")) continue;
                    JsonObject pool = WorldgenData.get("worldgen/template_pool", new ResourceLocation(st.get("start_pool").getAsString()));
                    if (pool == null) continue;
                    String loc = pool.getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("element").get("location").getAsString();
                    SETS.add(new Entry(sid, loc, st.has("project_start_to_heightmap") ? st.get("project_start_to_heightmap").getAsString() : null,
                        st.get("biomes").getAsString(), pl.get("spacing").getAsInt(), pl.get("separation").getAsInt(), pl.get("salt").getAsInt(),
                        st.has("start_height") ? WorldgenData.yValue(st.getAsJsonObject("start_height")) : 0));
                }
            }
        } catch (Exception e) {
            net.mcreator.boh.BohMod.LOGGER.error("Could not read structure sets", e);
        }
    }

    public static void generate(World w, int cx, int cz, ResourceLocation biome) {
        if (biome == null) return;
        for (Entry e : SETS) {
            if (!e.biomes.equals(biome.toString())) continue;
            int sx = Math.floorDiv(cx, e.spacing), sz = Math.floorDiv(cz, e.spacing);
            Random r = new Random(sx * 341873128712L + sz * 132897987541L + w.getSeed() + e.salt);
            int range = Math.max(1, e.spacing - e.separation);
            if (sx * e.spacing + r.nextInt(range) != cx || sz * e.spacing + r.nextInt(range) != cz) continue;
            StructureTemplate t = StructureTemplateManager.INSTANCE.getOrCreate(new ResourceLocation(e.pool));
            // centre of the 2x2 chunks that are guaranteed to exist while populating: a rotated piece stays inside them
            int x = cx * 16 + 16, z = cz * 16 + 16;
            // jigsaw: first free height at the start + start_height, minus the single pool element ground level delta (1)
            int y = e.heightmap == null ? e.startY + 64 : BohWorldProvider.baseHeight(w, x, z) + e.startY - 1;
            // void dimensions have no ground to sink into: 1.20 would put the bottom layer (the Boiler Room's floor)
            // below the world, so keep the whole template inside it
            if (y < 0) y = 0;
            Random pr = new Random(w.getSeed() ^ (cx * 31L + cz) * 0x9E3779B97F4A7C15L ^ e.salt);
            StructurePlaceSettings s = new StructurePlaceSettings().setRotation(Rotation.values()[pr.nextInt(4)])
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
            try {
                boolean ok = t.placeInWorldgen(w, new BlockPos(x, y, z), s, pr);
                if (LOGGED.add(e.structure)) net.mcreator.boh.BohMod.LOGGER.info("Structure {} ({}) at {} {} {}: {}", e.structure, e.pool, x, y, z,
                    ok ? "placed" : "template empty or not placed");
            } catch (Throwable ex) {
                if (LOGGED.add(e.structure + "!")) net.mcreator.boh.BohMod.LOGGER.error("Structure " + e.structure + " failed", ex);
            }
        }
    }

    /**
     * A standing spot inside the nearest structure of a mod biome (Boiler Room, Gaster's room, ...), or null. The mod
     * dimensions are void with one structure per grid cell, so arriving at the coordinates you left from (as the
     * original procedures do) usually means falling into the void; this finds the room the player should land in.
     * Loads (and so generates and populates) the chunks around it.
     */
    private static boolean hasRoof(World w, int x, int y, int z) {
        for (int yy = y; yy < Math.min(y + 16, w.getActualHeight()); yy++) if (w.getBlock(x, yy, z).getMaterial().blocksMovement()) return true;
        return false;
    }

    /** Why the last findArrival call returned null (for the self-test). */
    public static String lastArrivalMiss;

    public static double[] findArrival(World w, ResourceLocation biome, double px, double pz) {
        lastArrivalMiss = "no biome";
        if (biome == null) return null;
        int pcx = (int) Math.floor(px) >> 4, pcz = (int) Math.floor(pz) >> 4;
        Entry best = null;
        int bcx = 0, bcz = 0;
        long bestD = Long.MAX_VALUE;
        for (Entry e : SETS) {
            if (!e.biomes.equals(biome.toString())) continue;
            int range = Math.max(1, e.spacing - e.separation);
            for (int sx = Math.floorDiv(pcx, e.spacing) - 2; sx <= Math.floorDiv(pcx, e.spacing) + 2; sx++)
                for (int sz = Math.floorDiv(pcz, e.spacing) - 2; sz <= Math.floorDiv(pcz, e.spacing) + 2; sz++) {
                    // same start chunk as generate()
                    Random r = new Random(sx * 341873128712L + sz * 132897987541L + w.getSeed() + e.salt);
                    int cx = sx * e.spacing + r.nextInt(range), cz = sz * e.spacing + r.nextInt(range);
                    long dx = cx - pcx, dz = cz - pcz, d = dx * dx + dz * dz;
                    if (d < bestD) {
                        bestD = d;
                        best = e;
                        bcx = cx;
                        bcz = cz;
                    }
                }
        }
        if (best == null) {
            StringBuilder b = new StringBuilder("no structure set for biome " + biome + "; sets have:");
            for (Entry e : SETS) b.append(' ').append(e.biomes);
            lastArrivalMiss = b.toString();
            return null;
        }
        // load explicitly: getChunkFromChunkCoords can hand back the empty placeholder chunk for unloaded chunks;
        // with the 3x3 around the start loaded, the start chunk is populated and so the structure is placed
        for (int x = bcx - 1; x <= bcx + 2; x++) for (int z = bcz - 1; z <= bcz + 2; z++) {
            if (w instanceof net.minecraft.world.WorldServer) ((net.minecraft.world.WorldServer) w).theChunkProviderServer.loadChunk(x, z);
            else w.getChunkFromChunkCoords(x, z);
        }
        StructureTemplate t = StructureTemplateManager.INSTANCE.getOrCreate(new ResourceLocation(best.pool));
        Rotation rot = Rotation.values()[new Random(w.getSeed() ^ (bcx * 31L + bcz) * 0x9E3779B97F4A7C15L ^ best.salt).nextInt(4)];
        int hx = t.getSize().getX() / 2, hz = t.getSize().getZ() / 2;
        int[] c = rot == Rotation.CLOCKWISE_90 ? new int[] { -hz, hx } : rot == Rotation.CLOCKWISE_180 ? new int[] { -hx, -hz }
            : rot == Rotation.COUNTERCLOCKWISE_90 ? new int[] { hz, -hx } : new int[] { hx, hz };
        int ox = bcx * 16 + 16 + c[0], oz = bcz * 16 + 16 + c[1];
        // nearest column to the middle with a floor and two free blocks above it, lowest floor first; inside the room
        // (a roof above) before anywhere on top of it
        int reach = Math.max(8, Math.max(t.getSize().getX(), t.getSize().getZ()) / 2 + 1);
        for (int pass = 0; pass < 2; pass++)
            for (int rad = 0; rad <= reach; rad++)
                for (int dx = -rad; dx <= rad; dx++) for (int dz = -rad; dz <= rad; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != rad) continue;
                    int x = ox + dx, z = oz + dz;
                    for (int y = 1; y < w.getActualHeight() - 2; y++) {
                        if (w.getBlock(x, y - 1, z).getMaterial().blocksMovement() && !w.getBlock(x, y, z).getMaterial().blocksMovement()
                            && !w.getBlock(x, y + 1, z).getMaterial().blocksMovement() && (pass == 1 || hasRoof(w, x, y + 2, z)))
                            return new double[] { x + 0.5, y, z + 0.5 };
                    }
                }
        int solid = 0, minY = 999, maxY = -1;
        for (int x = bcx * 16 - 16; x < bcx * 16 + 48; x++) for (int z = bcz * 16 - 16; z < bcz * 16 + 48; z++) for (int y = 0; y < 64; y++)
            if (!w.isAirBlock(x, y, z)) {
                solid++;
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
        lastArrivalMiss = String.format("%s start chunk %d,%d rot %s size %s centre %d,%d: no floor within 8; %d blocks around it (y %d..%d), start populated %s",
            best.structure, bcx, bcz, rot, t.getSize(), ox, oz, solid, minY, maxY, w.getChunkFromChunkCoords(bcx, bcz).isTerrainPopulated);
        return null;
    }
}
