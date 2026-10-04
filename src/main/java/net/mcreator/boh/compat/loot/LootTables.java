package net.mcreator.boh.compat.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class LootTables {
    private static final Map<String, JsonObject> CACHE = new HashMap<>();

    private LootTables() {
    }

    private static synchronized JsonObject load(ResourceLocation id) {
        String key = id.toString();
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        } else {
            JsonObject obj = null;
            String res = "/data/" + id.getResourceDomain() + "/loot_tables/" + id.getResourcePath() + ".json";

            try (InputStream in = LootTables.class.getResourceAsStream(res)) {
                if (in != null) {
                    obj = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
                }
            } catch (Exception var9) {
            }

            CACHE.put(key, obj);
            return obj;
        }
    }

    public static boolean exists(ResourceLocation id) {
        return load(id) != null;
    }

    public static List<ItemStack> roll(ResourceLocation id, Random rand, BlockState state) {
        List<ItemStack> out = new ArrayList<>();
        JsonObject table = load(id);
        if (table != null && table.has("pools")) {
            for (JsonElement pe : table.getAsJsonArray("pools")) {
                JsonObject pool = pe.getAsJsonObject();
                if (conditionsPass(pool, rand, state)) {
                    int rolls = number(pool.get("rolls"), rand, 1);
                    JsonArray entries = pool.has("entries") ? pool.getAsJsonArray("entries") : new JsonArray();

                    for (int r = 0; r < rolls; r++) {
                        List<JsonObject> valid = new ArrayList<>();
                        int totalWeight = 0;

                        for (JsonElement ee : entries) {
                            JsonObject e = ee.getAsJsonObject();
                            if (conditionsPass(e, rand, state)) {
                                valid.add(e);
                                totalWeight += e.has("weight") ? e.get("weight").getAsInt() : 1;
                            }
                        }

                        if (!valid.isEmpty()) {
                            int pick = rand.nextInt(Math.max(1, totalWeight));

                            for (JsonObject e : valid) {
                                pick -= e.has("weight") ? e.get("weight").getAsInt() : 1;
                                if (pick < 0) {
                                    ItemStack s = entryStack(e, rand);
                                    if (s != null && s.stackSize > 0) {
                                        out.add(s);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            return out;
        } else {
            return out;
        }
    }

    private static ItemStack entryStack(JsonObject e, Random rand) {
        String type = e.has("type") ? e.get("type").getAsString() : "minecraft:item";
        if (!type.endsWith("item")) {
            return null;
        } else {
            LegacyIds.Target t = LegacyIds.target(e.get("name").getAsString());
            if (t == null) {
                return null;
            } else {
                Item item = t.item();
                if (item == null) {
                    return null;
                } else {
                    int count = 1;
                    if (e.has("functions")) {
                        for (JsonElement fe : e.getAsJsonArray("functions")) {
                            JsonObject f = fe.getAsJsonObject();
                            String fn = f.get("function").getAsString().replace("minecraft:", "");
                            if (fn.equals("set_count")) {
                                count = number(f.get("count"), rand, 1);
                            }
                        }
                    }

                    return new ItemStack(item, count, t.meta);
                }
            }
        }
    }

    private static boolean conditionsPass(JsonObject o, Random rand, BlockState state) {
        if (!o.has("conditions")) {
            return true;
        } else {
            for (JsonElement ce : o.getAsJsonArray("conditions")) {
                JsonObject c = ce.getAsJsonObject();
                String cond = c.get("condition").getAsString().replace("minecraft:", "");
                switch (cond) {
                    case "random_chance":
                        if (rand.nextFloat() >= c.get("chance").getAsFloat()) {
                            return false;
                        }
                        break;
                    case "block_state_property":
                        if (state != null && c.has("properties")) {
                            for (Entry<String, JsonElement> p : c.getAsJsonObject("properties").entrySet()) {
                                Property<?> prop = state.definition().getProperty(p.getKey());
                                if (prop != null) {
                                    String actual = nameOf(state, prop);
                                    if (!actual.equals(p.getValue().getAsString())) {
                                        return false;
                                    }
                                }
                            }
                        }
                }
            }

            return true;
        }
    }

    private static String nameOf(BlockState state, Property prop) {
        return prop.getName(state.getValue(prop));
    }

    private static int number(JsonElement e, Random rand, int def) {
        if (e == null) {
            return def;
        } else if (e.isJsonPrimitive()) {
            return (int)Math.floor(e.getAsDouble());
        } else {
            JsonObject o = e.getAsJsonObject();
            if (o.has("min") && o.has("max")) {
                int min = (int)Math.floor(o.get("min").getAsDouble());
                int max = (int)Math.floor(o.get("max").getAsDouble());
                return max <= min ? min : min + rand.nextInt(max - min + 1);
            } else {
                return o.has("value") ? (int)o.get("value").getAsDouble() : def;
            }
        }
    }

    public static void dropEntityLoot(EntityLivingBase e, boolean recentlyHit, int looting) {
        String name = EntityList.getEntityString(e);
        if (name != null) {
            String path = name.contains(".") ? name.substring(name.indexOf(46) + 1) : name;
            ResourceLocation id = new ResourceLocation("boh", "entities/" + path);
            if (exists(id)) {
                for (ItemStack s : roll(id, e.getRNG(), null)) {
                    e.entityDropItem(s, 0.0F);
                }
            }
        }
    }

    public static void fill(IInventory inv, ResourceLocation id, Random rand) {
        List<ItemStack> stacks = roll(id, rand, null);
        List<Integer> free = new ArrayList<>();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (inv.getStackInSlot(i) == null) {
                free.add(i);
            }
        }

        Collections.shuffle(free, rand);

        for (ItemStack s : stacks) {
            if (free.isEmpty()) {
                break;
            }

            inv.setInventorySlotContents(free.remove(free.size() - 1), s);
        }
    }
}
