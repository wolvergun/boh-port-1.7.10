package net.mcreator.boh.compat.debug;

import java.util.Arrays;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.world.gen.BohWorldProvider;
import net.mcreator.boh.compat.world.gen.Features;
import net.mcreator.boh.compat.world.gen.ModWorldGen;
import net.mcreator.boh.compat.world.gen.StructureSets;
import net.mcreator.boh.compat.world.gen.WorldgenData;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Headless checks run by the CI "selftest" workflow (dedicated server started with -Dboh.selftest=true): times chunk
 * generation in each mod dimension, counts block entities, checks that the room dimensions generate rooms with a
 * floor and where arrivals land, then stops the server. Results are log lines starting with [BOH-SELFTEST].
 */
public final class SelfTest {

    private int ticks;

    private SelfTest() {}

    public static void installIfEnabled() {
        if (System.getProperty("boh.selftest") == null) return;
        FMLCommonHandler.instance().bus().register(new SelfTest());
        log("enabled");
    }

    private static void log(String s, Object... args) {
        BohMod.LOGGER.info("[BOH-SELFTEST] " + String.format(s, args));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END || ++ticks != 40) return;
        MinecraftServer server = MinecraftServer.getServer();
        try {
            for (BohWorldProvider.Spec spec : BohWorldProvider.SPECS.values()) {
                WorldServer w = server.worldServerForDimension(spec.id);
                if (w == null) {
                    log("%s: dimension %d not loaded", spec.name, spec.id);
                    continue;
                }
                generation(w, spec);
                if (spec.floor == null) arrival(w, spec);
            }
            overworld(server.worldServerForDimension(0));
        } catch (Throwable t) {
            BohMod.LOGGER.error("[BOH-SELFTEST] failed", t);
        }
        log("done");
        server.initiateShutdown();
    }

    /** Loads a 12x12 chunk area far from anything generated and reports per-chunk load times and block entities. */
    private static void generation(WorldServer w, BohWorldProvider.Spec spec) {
        int base = 4000 + spec.id * 7;
        int n = 12;
        long[] ms = new long[n * n];
        long start = System.nanoTime();
        int i = 0;
        for (int cx = base; cx < base + n; cx++) for (int cz = base; cz < base + n; cz++) {
            long t = System.nanoTime();
            w.theChunkProviderServer.loadChunk(cx, cz);
            ms[i++] = (System.nanoTime() - t) / 1000;
        }
        long total = (System.nanoTime() - start) / 1_000_000;
        Arrays.sort(ms);
        int tes = 0, populated = 0, solid = 0;
        for (int cx = base + 1; cx < base + n - 1; cx++) for (int cz = base + 1; cz < base + n - 1; cz++) {
            Chunk c = w.getChunkFromChunkCoords(cx, cz);
            tes += c.chunkTileEntityMap.size();
            if (c.isTerrainPopulated) populated++;
            for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) for (int y = 0; y < 16; y++) if (c.getBlock(x, y, z) != Blocks.air) solid++;
        }
        int inner = (n - 2) * (n - 2);
        log("%s gen: %d chunks in %d ms, per chunk avg %.2f ms, p50 %.2f, p95 %.2f, max %.2f ms; inner %d chunks: %d populated, %.1f block entities/chunk, %.0f solid blocks/chunk in y0-15",
            spec.name, n * n, total, total / (double) (n * n), ms[ms.length / 2] / 1000.0, ms[ms.length * 95 / 100] / 1000.0, ms[ms.length - 1] / 1000.0,
            inner, populated, tes / (double) inner, solid / (double) inner);
    }

    /** Where a player arriving at a few coordinates would be put, and whether that spot is inside a room with a floor. */
    private static void arrival(WorldServer w, BohWorldProvider.Spec spec) {
        int[][] points = { { 0, 0 }, { 333, -571 }, { -1200, 800 } };
        for (int[] p : points) {
            double[] spot = StructureSets.findArrival(w, spec.biome.key, p[0], p[1]);
            if (spot == null) {
                log("%s arrival from %d,%d: NO ROOM FOUND (%s)", spec.name, p[0], p[1], StructureSets.lastArrivalMiss);
                continue;
            }
            int x = (int) Math.floor(spot[0]), y = (int) spot[1], z = (int) Math.floor(spot[2]);
            int blocks = 0, minY = 256, maxY = -1;
            for (int dx = -24; dx <= 24; dx++) for (int dz = -24; dz <= 24; dz++) for (int yy = 0; yy < 32; yy++) {
                if (w.getBlock(x + dx, yy, z + dz) != Blocks.air) {
                    blocks++;
                    minY = Math.min(minY, yy);
                    maxY = Math.max(maxY, yy);
                }
            }
            Block below = w.getBlock(x, y - 1, z);
            int roof = -1;
            for (int yy = y + 2; yy < y + 16; yy++) if (w.getBlock(x, yy, z).getMaterial().blocksMovement()) {
                roof = yy;
                break;
            }
            log("%s arrival from %d,%d -> %.1f %d %.1f: floor %s, roof at %d, room blocks %d (y %d..%d)", spec.name, p[0], p[1], spot[0], y, spot[2],
                Block.blockRegistry.getNameForObject(below), roof, blocks, minY, maxY);
        }
    }

    /**
     * Overworld features from the biome modifiers: each mod structure is forced (rarity ignored) in a chunk of one of
     * its biomes and must place every block; then a populated area is scanned for the common features.
     */
    private static void overworld(WorldServer w) {
        java.util.Map<net.minecraft.util.ResourceLocation, java.util.List<net.minecraft.world.biome.BiomeGenBase>> fb = ModWorldGen.featureBiomes();
        java.util.Random rand = new java.util.Random(42);
        for (java.util.Map.Entry<net.minecraft.util.ResourceLocation, java.util.List<net.minecraft.world.biome.BiomeGenBase>> e : fb.entrySet()) {
            net.minecraft.util.ResourceLocation id = e.getKey();
            com.google.gson.JsonObject placed = WorldgenData.get("worldgen/placed_feature", id);
            if (placed == null || !placed.get("feature").isJsonPrimitive()) continue;
            com.google.gson.JsonObject cf = WorldgenData.get("worldgen/configured_feature",
                new net.minecraft.util.ResourceLocation(placed.get("feature").getAsString()));
            if (cf == null || !"boh:structure_feature".equals(cf.get("type").getAsString())) continue;
            java.util.List<String> names = new java.util.ArrayList<>();
            for (net.minecraft.world.biome.BiomeGenBase b : e.getValue()) names.add(b.biomeName);
            net.minecraft.world.ChunkPosition at = null;
            for (int t = 0; t < 8 && at == null; t++)
                at = w.getWorldChunkManager().findBiomePosition(3000 + t * 2500, -2000 + t * 1700, 1000, e.getValue(), rand);
            if (at == null) {
                log("overworld %s: no %s found", id, names);
                continue;
            }
            String result = "no chunk passed its placement filters";
            int cx0 = at.chunkPosX >> 4, cz0 = at.chunkPosZ >> 4;
            search:
            for (int r = 0; r <= 4; r++) for (int dx = -r; dx <= r; dx++) for (int dz = -r; dz <= r; dz++) {
                if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;
                int cx = cx0 + dx, cz = cz0 + dz;
                for (int x = cx - 1; x <= cx + 2; x++) for (int z = cz - 1; z <= cz + 2; z++) w.theChunkProviderServer.loadChunk(x, z);
                net.minecraft.world.biome.BiomeGenBase b = w.getBiomeGenForCoords(cx * 16 + 16, cz * 16 + 16);
                if (!e.getValue().contains(b)) continue;
                StructureTemplate.lastBlocks = -1;
                java.util.List<int[]> pos = Features.place(id, w, rand, cx, cz, true);
                if (pos.isEmpty() || StructureTemplate.lastBlocks < 0) continue;
                int[] p = pos.get(0);
                result = String.format("placed at %d %d %d in %s: %d blocks, %d missed", p[0], p[1], p[2], b.biomeName, StructureTemplate.lastBlocks,
                    StructureTemplate.lastMissed);
                break search;
            }
            log("overworld %s (%s): %s", id, names, result);
        }
        // natural population, in plains (open grass: kindness flowers are in every overworld biome, spinel ore too)
        java.util.List<net.minecraft.world.biome.BiomeGenBase> plains = java.util.Collections.singletonList(net.minecraft.world.biome.BiomeGenBase.plains);
        net.minecraft.world.ChunkPosition pl = w.getWorldChunkManager().findBiomePosition(-4800, 4800, 1500, plains, rand);
        int bx = pl == null ? -4800 : pl.chunkPosX >> 4, bz = pl == null ? 4800 : pl.chunkPosZ >> 4;
        int flowers = 0, ore = 0, n = 0;
        java.util.Map<String, Integer> biomes = new java.util.TreeMap<>();
        Block flower = Block.getBlockFromName("boh:kindness_flower"), spinel = Block.getBlockFromName("boh:spinel_ore_ore");
        for (int cx = bx - 6; cx < bx + 6; cx++) for (int cz = bz - 6; cz < bz + 6; cz++) w.theChunkProviderServer.loadChunk(cx, cz);
        for (int cx = bx - 5; cx < bx + 5; cx++) for (int cz = bz - 5; cz < bz + 5; cz++) {
            Chunk c = w.getChunkFromChunkCoords(cx, cz);
            if (!c.isTerrainPopulated) continue;
            n++;
            biomes.merge(w.getBiomeGenForCoords(cx * 16 + 8, cz * 16 + 8).biomeName, 1, Integer::sum);
            for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) for (int y = 1; y < 128; y++) {
                Block b = c.getBlock(x, y, z);
                if (b == flower) flowers++;
                else if (b == spinel) ore++;
            }
        }
        log("overworld population around chunk %d,%d: %d chunks %s, %d kindness flowers, %.1f spinel ore/chunk", bx, bz, n, biomes, flowers,
            ore / (double) Math.max(1, n));
    }
}
