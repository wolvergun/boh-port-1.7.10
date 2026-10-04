package net.mcreator.boh.compat.advancement;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 1.20 advancements of the mod (data/boh/advancements) as 1.7.10 achievements on a "Box of Horrors" page.
 * Title/description keys: achievement.boh.&lt;name&gt; / achievement.boh.&lt;name&gt;.desc.
 */
public final class Advancements {

    private static final String[] FILES = { "start_boh", "craft_computer_achievement", "grimm_start", "pasta_night",
        "big_top_burger_achievement", "obtain_first_document_achievement", "requiemfor_two_killers", "slender_gift" };

    private static final Map<String, Advancement> BY_ID = new LinkedHashMap<>();
    /** advancement id -> block ids whose placement grants it (placed_block trigger) */
    private static final Map<String, List<String>> PLACED_BLOCK = new HashMap<>();

    private Advancements() {}

    public static void init() {
        Map<String, JsonObject> defs = new LinkedHashMap<>();
        for (String f : FILES) {
            try (InputStream in = Advancements.class.getResourceAsStream("/data/boh/advancements/" + f + ".json")) {
                if (in == null) continue;
                defs.put("boh:" + f, new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject());
            } catch (Exception e) {
                BohMod.LOGGER.error("Could not read advancement " + f, e);
            }
        }
        Map<String, Integer> depth = new HashMap<>();
        Map<Integer, Integer> rowsAtDepth = new HashMap<>();
        List<Achievement> page = new ArrayList<>();
        // parents are listed before children in FILES
        for (Map.Entry<String, JsonObject> e : defs.entrySet()) {
            String id = e.getKey();
            JsonObject o = e.getValue();
            String parentId = o.has("parent") ? o.get("parent").getAsString() : null;
            Advancement parent = parentId == null ? null : BY_ID.get(parentId);
            int d = parent == null ? 0 : depth.get(parentId) + 1;
            depth.put(id, d);
            int row = rowsAtDepth.merge(d, 1, Integer::sum) - 1;
            JsonObject display = o.getAsJsonObject("display");
            ItemStack icon = icon(display.getAsJsonObject("icon").get("item").getAsString());
            String name = "boh." + id.substring(4);
            Achievement a = new Achievement("achievement." + name, name, d * 2 - 1, row * 2 - 1, icon,
                parent == null ? null : parent.achievement);
            if (parent == null) a.initIndependentStat();
            if ("challenge".equals(display.has("frame") ? display.get("frame").getAsString() : "")) a.setSpecial();
            a.registerStat();
            page.add(a);
            Advancement adv = new Advancement(id, a, parent);
            BY_ID.put(id, adv);
            for (Map.Entry<String, JsonElement> c : o.getAsJsonObject("criteria").entrySet()) {
                JsonObject crit = c.getValue().getAsJsonObject();
                if (!"minecraft:placed_block".equals(crit.get("trigger").getAsString()) || !crit.has("conditions")) continue;
                JsonArray loc = crit.getAsJsonObject("conditions").getAsJsonArray("location");
                for (JsonElement l : loc) {
                    JsonObject lo = l.getAsJsonObject();
                    if (lo.has("block")) PLACED_BLOCK.computeIfAbsent(id, k -> new ArrayList<>()).add(lo.get("block").getAsString());
                }
            }
        }
        AchievementPage.registerAchievementPage(new AchievementPage("Box of Horrors", page.toArray(new Achievement[0])));
        MinecraftForge.EVENT_BUS.register(new Advancements.Hooks());
    }

    private static ItemStack icon(String id) {
        Object o = Item.itemRegistry.getObject(id);
        if (o instanceof Item) return new ItemStack((Item) o);
        LegacyIds.Target t = LegacyIds.target(id);
        if (t != null && t.item() != null) return new ItemStack(t.item(), 1, t.meta);
        return new ItemStack(Items.paper);
    }

    public static Advancement get(String id) {
        return BY_ID.get(id.contains(":") ? id : "boh:" + id);
    }

    public static boolean isDone(EntityPlayer p, Advancement a) {
        if (a == null) return false;
        if (p instanceof EntityPlayerMP) return ((EntityPlayerMP) p).func_147099_x().hasAchievementUnlocked(a.achievement);
        return false;
    }

    /** Grants an advancement (and its locked ancestors, which 1.7.10 requires). */
    public static void grant(EntityPlayerMP p, Advancement a) {
        if (a == null || isDone(p, a)) return;
        if (a.parent != null) grant(p, a.parent);
        p.triggerAchievement(a.achievement);
    }

    public static void grant(EntityPlayerMP p, String id) {
        grant(p, get(id));
    }

    public static void revoke(EntityPlayerMP p, Advancement a) {
        if (a != null) p.func_147099_x().func_150873_a(p, a.achievement, 0);
    }

    public static final class Hooks {

        @SubscribeEvent
        public void onPlace(BlockEvent.PlaceEvent e) {
            if (!(e.player instanceof EntityPlayerMP)) return;
            String placed = Block.blockRegistry.getNameForObject(e.placedBlock);
            for (Map.Entry<String, List<String>> en : PLACED_BLOCK.entrySet())
                if (en.getValue().contains(placed)) grant((EntityPlayerMP) e.player, BY_ID.get(en.getKey()));
        }
    }
}
