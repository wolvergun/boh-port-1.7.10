package net.mcreator.boh.compat.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Random;
import java.util.Map.Entry;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public final class StateParser {
    private StateParser() {
    }

    public static BlockState parse(JsonObject o) {
        if (o != null && o.has("Name")) {
            String name = o.get("Name").getAsString();
            Block b = Block.getBlockFromName(name);
            int meta = 0;
            if (b == null || b == Blocks.air && !name.endsWith("air")) {
                LegacyIds.Target t = LegacyIds.target(name);
                if (t == null) {
                    return null;
                }

                b = t.block();
                meta = t.meta;
            }

            BlockState s = BlockState.of(b, meta);
            if (b instanceof BohBlock && o.has("Properties")) {
                s = ((BohBlock)b).defaultBlockState();

                for (Entry<String, JsonElement> e : o.getAsJsonObject("Properties").entrySet()) {
                    Property p = s.definition().getProperty(e.getKey());
                    if (p != null) {
                        Object v = p.getValue(e.getValue().getAsString()).orElse(null);
                        if (v != null) {
                            s = s.setValue(p, (Comparable)v);
                        }
                    }
                }
            } else if (b instanceof BohBlock) {
                s = ((BohBlock)b).defaultBlockState();
            }

            return s;
        } else {
            return null;
        }
    }

    public static BlockState provider(JsonObject p, Random r) {
        if (p == null) {
            return null;
        } else if (p.has("state")) {
            return parse(p.getAsJsonObject("state"));
        } else {
            if (p.has("entries")) {
                JsonArray a = p.getAsJsonArray("entries");
                int total = 0;

                for (JsonElement e : a) {
                    total += e.getAsJsonObject().has("weight") ? e.getAsJsonObject().get("weight").getAsInt() : 1;
                }

                int pick = r.nextInt(Math.max(1, total));

                for (JsonElement e : a) {
                    JsonObject eo = e.getAsJsonObject();
                    pick -= eo.has("weight") ? eo.get("weight").getAsInt() : 1;
                    if (pick < 0) {
                        return parse(eo.getAsJsonObject("data"));
                    }
                }
            }

            return null;
        }
    }
}
