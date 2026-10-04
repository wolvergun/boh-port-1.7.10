package net.mcreator.boh.compat.registry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.mcreator.boh.BohMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/** Registers the mod's 1.20 JSON recipes (data/boh/recipes) as 1.7.10 crafting and smelting recipes. */
public final class Recipes {

    private static int altCounter;

    private Recipes() {}

    public static void init() {
        int ok = 0, skipped = 0;
        try (InputStream in = Recipes.class.getResourceAsStream("/assets/boh/compat/recipes.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String name;
            while ((name = r.readLine()) != null) {
                name = name.trim();
                if (name.isEmpty()) continue;
                try {
                    if (register(name)) ok++;
                    else skipped++;
                } catch (Exception e) {
                    skipped++;
                    BohMod.LOGGER.warn("Recipe boh:{} skipped: {}", name, e.toString());
                }
            }
        } catch (Exception e) {
            BohMod.LOGGER.error("Could not read recipes", e);
        }
        BohMod.LOGGER.info("Registered {} recipes ({} skipped)", ok, skipped);
    }

    private static boolean register(String name) throws Exception {
        JsonObject o;
        try (InputStream in = Recipes.class.getResourceAsStream("/data/boh/recipes/" + name + ".json")) {
            if (in == null) return false;
            o = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        }
        String type = o.get("type").getAsString().replace("minecraft:", "");
        switch (type) {
            case "crafting_shaped": {
                ItemStack out = result(o.get("result"));
                if (out == null) return false;
                List<Object> args = new ArrayList<>();
                for (JsonElement row : o.getAsJsonArray("pattern")) args.add(row.getAsString());
                for (Map.Entry<String, JsonElement> k : o.getAsJsonObject("key").entrySet()) {
                    Object ing = ingredient(k.getValue());
                    if (ing == null) return false;
                    args.add(k.getKey().charAt(0));
                    args.add(ing);
                }
                GameRegistry.addRecipe(new ShapedOreRecipe(out, args.toArray()));
                return true;
            }
            case "crafting_shapeless": {
                ItemStack out = result(o.get("result"));
                if (out == null) return false;
                List<Object> args = new ArrayList<>();
                for (JsonElement e : o.getAsJsonArray("ingredients")) {
                    Object ing = ingredient(e);
                    if (ing == null) return false;
                    args.add(ing);
                }
                GameRegistry.addRecipe(new ShapelessOreRecipe(out, args.toArray()));
                return true;
            }
            case "smelting":
            case "smoking":
            case "campfire_cooking":
            case "blasting": {
                ItemStack out = result(o.get("result"));
                Object in = ingredient(o.get("ingredient"));
                if (out == null || in == null) return false;
                float xp = o.has("experience") ? o.get("experience").getAsFloat() : 0;
                List<ItemStack> inputs = in instanceof ItemStack ? java.util.Collections.singletonList((ItemStack) in) : OreDictionary.getOres((String) in);
                for (ItemStack s : inputs) {
                    if (net.minecraft.item.crafting.FurnaceRecipes.smelting().getSmeltingResult(s) == null) GameRegistry.addSmelting(s, out, xp);
                }
                return true;
            }
            default:
                return false;
        }
    }

    private static ItemStack result(JsonElement e) {
        if (e.isJsonPrimitive()) return stack(e.getAsString(), 1);
        JsonObject o = e.getAsJsonObject();
        return stack(o.get("item").getAsString(), o.has("count") ? o.get("count").getAsInt() : 1);
    }

    /** ItemStack, or an ore dictionary name for tags and alternatives. */
    private static Object ingredient(JsonElement e) {
        if (e.isJsonArray()) {
            JsonArray a = e.getAsJsonArray();
            if (a.size() == 1) return ingredient(a.get(0));
            String ore = "boh_alt_" + (altCounter++);
            for (JsonElement x : a) {
                Object i = ingredient(x);
                if (i instanceof ItemStack) OreDictionary.registerOre(ore, (ItemStack) i);
                else if (i instanceof String) for (ItemStack s : OreDictionary.getOres((String) i)) OreDictionary.registerOre(ore, s);
            }
            return OreDictionary.getOres(ore).isEmpty() ? null : ore;
        }
        JsonObject o = e.getAsJsonObject();
        if (o.has("tag")) return tag(o.get("tag").getAsString());
        return stack(o.get("item").getAsString(), 1);
    }

    private static Object tag(String tag) {
        switch (tag) {
            case "minecraft:planks":
                return "plankWood";
            case "minecraft:logs":
            case "minecraft:logs_that_burn":
                return "logWood";
            case "minecraft:wool":
                return new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE);
            case "minecraft:wooden_slabs":
                return "slabWood";
            case "minecraft:saplings":
                return "treeSapling";
            default: {
                String ore = "boh_tag_" + tag.replace(':', '_').replace('/', '_');
                for (String id : net.mcreator.boh.compat.mc.tags.TagKey.<Object>of("items", new net.minecraft.util.ResourceLocation(tag)).members()) {
                    ItemStack s = stack(id, 1);
                    if (s != null) OreDictionary.registerOre(ore, s);
                }
                return OreDictionary.getOres(ore).isEmpty() ? null : ore;
            }
        }
    }

    static ItemStack stack(String id, int count) {
        Object o = Item.itemRegistry.getObject(id);
        if (o instanceof Item) return new ItemStack((Item) o, count);
        Block b = Block.getBlockFromName(id);
        if (b != null && b != Blocks.air) return new ItemStack(b, count);
        LegacyIds.Target t = LegacyIds.target(id);
        if (t == null) return null;
        Item i = t.item();
        return i == null ? null : new ItemStack(i, count, t.meta);
    }
}
