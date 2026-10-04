package net.mcreator.boh.compat.world.gen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.mcreator.boh.compat.world.StateParser;
import net.mcreator.boh.compat.world.TreePlacer;
import net.mcreator.boh.compat.world.VanillaStates;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public final class Features {
    private Features() {
    }

    public static void place(ResourceLocation placedId, World w, Random r, int chunkX, int chunkZ) {
        if (!"minecraft".equals(placedId.getResourceDomain())) {
            JsonObject placed = WorldgenData.get("worldgen/placed_feature", placedId);
            if (placed != null) {
                List<int[]> positions = new ArrayList<>();
                positions.add(new int[]{chunkX * 16 + 8, 0, chunkZ * 16 + 8});
                if (placed.has("placement")) {
                    for (JsonElement m : placed.getAsJsonArray("placement")) {
                        positions = modify(m.getAsJsonObject(), positions, w, r);
                    }
                }

                if (!positions.isEmpty()) {
                    JsonElement fe = placed.get("feature");
                    JsonObject configured = fe.isJsonObject()
                        ? fe.getAsJsonObject()
                        : WorldgenData.get("worldgen/configured_feature", new ResourceLocation(fe.getAsString()));
                    if (configured != null) {
                        ResourceLocation cfId = fe.isJsonPrimitive() ? new ResourceLocation(fe.getAsString()) : null;

                        for (int[] p : positions) {
                            run(configured, cfId, w, r, p);
                        }
                    }
                }
            }
        }
    }

    private static List<int[]> modify(JsonObject m, List<int[]> in, World w, Random r) {
        String type = m.get("type").getAsString().replace("minecraft:", "");
        List<int[]> out = new ArrayList<>();

        for (int[] p : in) {
            switch (type) {
                case "count":
                case "count_on_every_layer":
                    int n = WorldgenData.intProvider(m.get("count"), r, 1);

                    for (int i = 0; i < n; i++) {
                        out.add((int[])p.clone());
                    }
                    break;
                case "noise_based_count":
                case "noise_threshold_count":
                    out.add((int[])p.clone());
                    break;
                case "rarity_filter":
                    if (r.nextInt(Math.max(1, m.get("chance").getAsInt())) == 0) {
                        out.add(p);
                    }
                    break;
                case "in_square":
                    out.add(new int[]{p[0] + r.nextInt(16), p[1], p[2] + r.nextInt(16)});
                    break;
                case "heightmap":
                    String hm = m.has("heightmap") ? m.get("heightmap").getAsString() : "WORLD_SURFACE_WG";
                    int y = !hm.startsWith("OCEAN_FLOOR") && !hm.contains("NO_LEAVES") ? w.getHeightValue(p[0], p[2]) : w.getTopSolidOrLiquidBlock(p[0], p[2]);
                    out.add(new int[]{p[0], y, p[2]});
                    break;
                case "height_range":
                    JsonObject h = m.getAsJsonObject("height");
                    int lo = 0;
                    int hi = 0;
                    if (h.has("min_inclusive")) {
                        lo = WorldgenData.yValue(h.get("min_inclusive"));
                        hi = WorldgenData.yValue(h.get("max_inclusive"));
                    } else if (h.has("absolute")) {
                        lo = hi = h.get("absolute").getAsInt();
                    }

                    lo = Math.max(1, lo + (lo < 0 ? 64 : 0));
                    hi = Math.max(lo, Math.min(255, hi + (hi < 0 ? 64 : 0)));
                    out.add(new int[]{p[0], lo + r.nextInt(hi - lo + 1), p[2]});
                    break;
                case "random_offset":
                    int dx = WorldgenData.intProvider(m.get("xz_spread"), r, 0);
                    int dy = WorldgenData.intProvider(m.get("y_spread"), r, 0);
                    int dz = WorldgenData.intProvider(m.get("xz_spread"), r, 0);
                    out.add(new int[]{p[0] + dx, p[1] + dy, p[2] + dz});
                    break;
                case "surface_water_depth_filter":
                    int max = m.get("max_water_depth").getAsInt();
                    int depth = 0;

                    for (int y = p[1]; y < 256 && w.getBlock(p[0], y, p[2]).getMaterial() == Material.water; y++) {
                        depth++;
                    }

                    if (depth <= max) {
                        out.add(p);
                    }
                    break;
                case "block_predicate_filter":
                    if (test(m.getAsJsonObject("predicate"), w, p)) {
                        out.add(p);
                    }
                    break;
                case "environment_scan":
                case "surface_relative_threshold_filter":
                case "biome":
                default:
                    out.add(p);
            }
        }

        return out;
    }

    static boolean test(JsonObject pred, World w, int[] p) {
        String t = pred.get("type").getAsString().replace("minecraft:", "");
        int ox = 0;
        int oy = 0;
        int oz = 0;
        if (pred.has("offset")) {
            JsonArray o = pred.getAsJsonArray("offset");
            ox = o.get(0).getAsInt();
            oy = o.get(1).getAsInt();
            oz = o.get(2).getAsInt();
        }

        int x = p[0] + ox;
        int y = p[1] + oy;
        int z = p[2] + oz;
        if (y >= 0 && y <= 255) {
            Block b = w.getBlock(x, y, z);
            switch (t) {
                case "matching_blocks":
                    JsonElement bl = pred.get("blocks");
                    List<String> ids = new ArrayList<>();
                    if (bl.isJsonArray()) {
                        for (JsonElement exx : bl.getAsJsonArray()) {
                            ids.add(exx.getAsString());
                        }
                    } else {
                        ids.add(bl.getAsString());
                    }

                    for (String id : ids) {
                        if (matches(b, w.getBlockMetadata(x, y, z), id)) {
                            return true;
                        }
                    }

                    return false;
                case "solid":
                    return b.getMaterial().isSolid();
                case "replaceable":
                    return b.isReplaceable(w, x, y, z);
                case "would_survive":
                    BlockState s = pred.has("state") ? StateParser.parse(pred.getAsJsonObject("state")) : null;
                    return s == null || s.getBlock().canBlockStay(w, x, y, z) || s.getBlock().canPlaceBlockAt(w, x, y, z);
                case "all_of":
                    for (JsonElement ex : pred.getAsJsonArray("predicates")) {
                        if (!test(ex.getAsJsonObject(), w, p)) {
                            return false;
                        }
                    }

                    return true;
                case "any_of":
                    for (JsonElement e : pred.getAsJsonArray("predicates")) {
                        if (test(e.getAsJsonObject(), w, p)) {
                            return true;
                        }
                    }

                    return false;
                case "not":
                    return !test(pred.getAsJsonObject("predicate"), w, p);
                case "true":
                default:
                    return true;
            }
        } else {
            return false;
        }
    }

    static boolean matches(Block b, int meta, String id) {
        if (id.equals("minecraft:air")) {
            return b.getMaterial() == Material.air;
        } else {
            VanillaStates.Legacy l = VanillaStates.convert(id, null);
            if (l == null) {
                return false;
            } else {
                return l.block != Blocks.grass ? l.block == b : b == Blocks.grass || b == Blocks.mycelium;
            }
        }
    }

    private static void run(JsonObject cf, ResourceLocation cfId, World w, Random r, int[] p) {
        String type = cf.get("type").getAsString();
        JsonObject c = cf.has("config") ? cf.getAsJsonObject("config") : new JsonObject();
        switch (type) {
            case "boh:structure_feature":
                StructureTemplate t = StructureTemplateManager.INSTANCE.getOrCreate(new ResourceLocation(c.get("structure").getAsString()));
                StructurePlaceSettings s = new StructurePlaceSettings();
                if (c.has("random_rotation") && c.get("random_rotation").getAsBoolean()) {
                    s.setRotation(Rotation.values()[r.nextInt(4)]);
                }

                if (c.has("random_mirror") && c.get("random_mirror").getAsBoolean()) {
                    s.setMirror(Mirror.values()[r.nextInt(3)]);
                }

                if (c.has("ignored_blocks")) {
                    JsonElement ib = c.get("ignored_blocks");
                    List<String> ids = new ArrayList<>();
                    if (ib.isJsonArray()) {
                        for (JsonElement e : ib.getAsJsonArray()) {
                            ids.add(e.getAsString());
                        }
                    } else {
                        ids.add(ib.getAsString());
                    }

                    s.addProcessor(new BlockIgnoreProcessor(ids.toArray(new String[0])));
                }

                int[] off = new int[]{0, 0, 0};
                if (c.has("offset")) {
                    JsonArray o = c.getAsJsonArray("offset");

                    for (int ix = 0; ix < 3; ix++) {
                        off[ix] = o.get(ix).getAsInt();
                    }
                }

                t.placeInWorld(w, new BlockPos(p[0] + off[0], p[1] + off[1], p[2] + off[2]), new BlockPos(0, 0, 0), s, r, 2);
                break;
            case "minecraft:tree":
                if (cfId != null) {
                    TreePlacer.place(cfId, w, new BlockPos(p[0], p[1], p[2]), r);
                }
                break;
            case "minecraft:ore":
                JsonArray targets = c.getAsJsonArray("targets");
                if (targets != null && targets.size() != 0) {
                    BlockState s = StateParser.parse(targets.get(0).getAsJsonObject().getAsJsonObject("state"));
                    if (s != null) {
                        new WorldGenMinable(s.getBlock(), s.meta(), c.get("size").getAsInt(), Blocks.stone).generate(w, r, p[0], p[1], p[2]);
                    }
                }
                break;
            case "minecraft:random_patch":
            case "minecraft:flower":
            case "minecraft:no_bonemeal_flower":
                int tries = c.has("tries") ? c.get("tries").getAsInt() : 128;
                int xz = c.has("xz_spread") ? c.get("xz_spread").getAsInt() : 7;
                int ys = c.has("y_spread") ? c.get("y_spread").getAsInt() : 3;
                JsonObject inner = c.getAsJsonObject("feature");
                JsonObject innerFeature = inner.has("feature") && inner.get("feature").isJsonObject() ? inner.getAsJsonObject("feature") : null;
                if (innerFeature != null && innerFeature.get("type").getAsString().endsWith("simple_block")) {
                    BlockState s = StateParser.provider(innerFeature.getAsJsonObject("config").getAsJsonObject("to_place"), r);
                    if (s != null) {
                        for (int i = 0; i < tries; i++) {
                            int x = p[0] + r.nextInt(xz + 1) - r.nextInt(xz + 1);
                            int y = p[1] + r.nextInt(ys + 1) - r.nextInt(ys + 1);
                            int z = p[2] + r.nextInt(xz + 1) - r.nextInt(xz + 1);
                            if (y >= 1
                                && y <= 255
                                && w.isAirBlock(x, y, z)
                                && (s.getBlock().canBlockStay(w, x, y, z) || s.getBlock().canPlaceBlockAt(w, x, y, z))) {
                                M.setBlock(w, new BlockPos(x, y, z), s, 2);
                            }
                        }
                    }
                }
                break;
            case "minecraft:vegetation_patch":
                BlockState ground = StateParser.provider(c.getAsJsonObject("ground_state"), r);
                if (ground != null) {
                    int rad = WorldgenData.intProvider(c.get("xz_radius"), r, 3);
                    int depth = WorldgenData.intProvider(c.get("depth"), r, 1);

                    for (int dx = -rad; dx <= rad; dx++) {
                        for (int dz = -rad; dz <= rad; dz++) {
                            if (dx * dx + dz * dz <= rad * rad + 1 && (Math.abs(dx) != rad && Math.abs(dz) != rad || !r.nextBoolean())) {
                                int x = p[0] + dx;
                                int z = p[2] + dz;
                                int top = w.getTopSolidOrLiquidBlock(x, z) - 1;

                                for (int d = 0; d < depth; d++) {
                                    Block b = w.getBlock(x, top - d, z);
                                    if (b == Blocks.grass
                                        || b == Blocks.dirt
                                        || b == Blocks.stone
                                        || b == Blocks.gravel
                                        || b == Blocks.sand) {
                                        M.setBlock(w, new BlockPos(x, top - d, z), ground, 2);
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}
