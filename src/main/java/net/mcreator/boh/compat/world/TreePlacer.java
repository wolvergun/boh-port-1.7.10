package net.mcreator.boh.compat.world;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.SaplingBlock;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public final class TreePlacer {
    private TreePlacer() {
    }

    public static JsonObject read(ResourceLocation id) {
        String path = "/data/" + id.getResourceDomain() + "/worldgen/configured_feature/" + id.getResourcePath() + ".json";

        try {
            JsonObject var3;
            try (InputStream in = TreePlacer.class.getResourceAsStream(path)) {
                if (in == null) {
                    return null;
                }

                var3 = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            }

            return var3;
        } catch (Exception var7) {
            return null;
        }
    }

    public static boolean place(ResourceLocation feature, World w, BlockPos pos, Random r) {
        JsonObject f = read(feature);
        if (f != null && f.get("type").getAsString().endsWith("tree")) {
            JsonObject c = f.getAsJsonObject("config");
            BlockState log = StateParser.provider(c.getAsJsonObject("trunk_provider"), r);
            BlockState leaves = StateParser.provider(c.getAsJsonObject("foliage_provider"), r);
            if (log != null && leaves != null) {
                JsonObject tp = c.getAsJsonObject("trunk_placer");
                int height = i(tp, "base_height", 4) + r.nextInt(i(tp, "height_rand_a", 0) + 1) + r.nextInt(i(tp, "height_rand_b", 0) + 1);
                JsonObject fp = c.getAsJsonObject("foliage_placer");
                int radius = Math.max(1, i(fp, "radius", 2));
                int fh = Math.max(2, i(fp, "height", 3));
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                if (y >= 1 && y + height + 2 <= w.getHeight()) {
                    for (int dy = 1; dy <= height; dy++) {
                        if (!replaceable(w, x, y + dy, z)) {
                            return false;
                        }
                    }

                    Block below = w.getBlock(x, y - 1, z);
                    if (below == Blocks.grass || below == Blocks.farmland) {
                        w.setBlock(x, y - 1, z, Blocks.dirt, 0, 2);
                    }

                    String type = tp.get("type").getAsString();

                    for (int dyx = 0; dyx < height; dyx++) {
                        M.setBlock(w, new BlockPos(x, y + dyx, z), log, 2);
                    }

                    int top = y + height;
                    blob(w, x, top, z, radius, fh, leaves, r);
                    if (type.contains("cherry") || type.contains("fancy") || type.contains("forking") || type.contains("bending")) {
                        int branches = 1 + r.nextInt(3);

                        for (int b = 0; b < branches; b++) {
                            int dx = r.nextInt(3) - 1;
                            int dz = dx == 0 ? (r.nextBoolean() ? 1 : -1) : 0;
                            int len = 2 + r.nextInt(2);
                            int by = y + height - 1 - r.nextInt(2);
                            int bx = x;
                            int bz = z;

                            for (int s = 1; s <= len; s++) {
                                bx += dx;
                                bz += dz;
                                if (s == len) {
                                    by++;
                                }

                                if (replaceable(w, bx, by, bz)) {
                                    M.setBlock(w, new BlockPos(bx, by, bz), log, 2);
                                }
                            }

                            blob(w, bx, by + 1, bz, Math.max(1, radius - 1), Math.max(2, fh - 1), leaves, r);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static void blob(World w, int cx, int topY, int cz, int radius, int height, BlockState leaves, Random r) {
        for (int dy = -height + 1; dy <= 1; dy++) {
            int rad = dy == 1 ? Math.max(1, radius - 1) : radius;

            for (int dx = -rad; dx <= rad; dx++) {
                for (int dz = -rad; dz <= rad; dz++) {
                    boolean corner = Math.abs(dx) == rad && Math.abs(dz) == rad;
                    if ((!corner || dy != 1 && r.nextInt(2) != 0) && dx * dx + dz * dz <= rad * rad + 1) {
                        int x = cx + dx;
                        int y = topY + dy;
                        int z = cz + dz;
                        if (w.isAirBlock(x, y, z) || w.getBlock(x, y, z).isLeaves(w, x, y, z)) {
                            M.setBlock(w, new BlockPos(x, y, z), leaves, 2);
                        }
                    }
                }
            }
        }
    }

    private static boolean replaceable(World w, int x, int y, int z) {
        Block b = w.getBlock(x, y, z);
        return b.isAir(w, x, y, z)
            || b.isLeaves(w, x, y, z)
            || b.getMaterial() == Material.plants
            || b.getMaterial() == Material.vine
            || b.isReplaceable(w, x, y, z)
            || b instanceof SaplingBlock;
    }

    static int i(JsonObject o, String key, int def) {
        if (o != null && o.has(key)) {
            JsonElement e = o.get(key);
            if (e.isJsonPrimitive()) {
                return e.getAsInt();
            } else {
                if (e.isJsonObject()) {
                    JsonObject v = e.getAsJsonObject();
                    if (v.has("value") && v.get("value").isJsonPrimitive()) {
                        return v.get("value").getAsInt();
                    }

                    if (v.has("value") && v.get("value").isJsonObject()) {
                        JsonObject u = v.getAsJsonObject("value");
                        if (u.has("min_inclusive")) {
                            return u.get("min_inclusive").getAsInt();
                        }
                    }
                }

                return def;
            }
        } else {
            return def;
        }
    }
}
