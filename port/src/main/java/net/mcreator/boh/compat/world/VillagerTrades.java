package net.mcreator.boh.compat.world;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.common.BasicItemListing;
import net.mcreator.boh.compat.forge.event.village.VillagerTradesEvent;
import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.registry.VillagerRegistry;

/**
 * Feeds the 1.20 VillagerTradesEvent listings (levels 1-5) to a 1.7.10 profession. 1.7.10 villagers have no levels:
 * they start with one trade and unlock a random new one each time a trade is used. To keep the original order roughly,
 * a villager is offered the trades of level 1 + (trades it already has) / 2, like 1.20's two trades per level.
 */
public final class VillagerTrades {

    private static final Map<VillagerProfession, Map<Integer, List<Object>>> TRADES = new HashMap<>();
    private static Field buyingList;

    private VillagerTrades() {}

    public static void register(VillagerProfession profession, int legacyId) {
        VillagerRegistry.instance().registerVillageTradeHandler(legacyId,
            (villager, recipes, random) -> addTrades(profession, villager, recipes, random));
    }

    private static Map<Integer, List<Object>> trades(VillagerProfession p) {
        return TRADES.computeIfAbsent(p, k -> {
            VillagerTradesEvent ev = new VillagerTradesEvent(k);
            MinecraftForge.EVENT_BUS.post(ev);
            return ev.getTrades();
        });
    }

    static void addTrades(VillagerProfession p, EntityVillager villager, MerchantRecipeList recipes, Random random) {
        int level = Math.min(5, 1 + knownTrades(villager) / 2);
        for (int l = 1; l <= level; l++) {
            List<Object> listings = trades(p).get(l);
            if (listings == null) continue;
            for (Object o : listings) {
                if (!(o instanceof BasicItemListing)) continue;
                BasicItemListing b = (BasicItemListing) o;
                // trades for items 1.7.10 does not have (amethyst shards, ...) have an empty price or result
                if (M.legacy(b.price) == null || M.legacy(b.forSale) == null) continue;
                MerchantRecipe r = b.toRecipe();
                recipes.add(r);
            }
        }
    }

    private static int knownTrades(EntityVillager villager) {
        try {
            if (buyingList == null) {
                try {
                    buyingList = EntityVillager.class.getDeclaredField("buyingList");
                } catch (NoSuchFieldException e) {
                    buyingList = EntityVillager.class.getDeclaredField("field_70963_i");
                }
                buyingList.setAccessible(true);
            }
            Object list = buyingList.get(villager);
            return list instanceof List ? ((List<?>) list).size() : 0;
        } catch (ReflectiveOperationException e) {
            BohMod.LOGGER.warn("Could not read villager trades", e);
            return 0;
        }
    }
}
