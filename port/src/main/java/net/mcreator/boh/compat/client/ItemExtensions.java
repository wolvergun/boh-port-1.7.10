package net.mcreator.boh.compat.client;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.item.BohItem;
import net.minecraft.item.Item;

/** Client extensions of items, collected lazily from their 1.20 initializeClient(). */
public final class ItemExtensions {

    private static final Map<Item, IClientItemExtensions> CACHE = new HashMap<>();

    private ItemExtensions() {}

    public static IClientItemExtensions of(Item item) {
        IClientItemExtensions e = CACHE.get(item);
        if (e != null) return e;
        IClientItemExtensions[] holder = { IClientItemExtensions.DEFAULT };
        if (item instanceof BohItem || item instanceof net.mcreator.boh.compat.item.BohBlockItem) {
            try {
                if (item instanceof BohItem) ((BohItem) item).initializeClient(x -> holder[0] = x);
                else ((net.mcreator.boh.compat.item.BohBlockItem) item).initializeClient(x -> holder[0] = x);
            } catch (Throwable t) {
                net.mcreator.boh.BohMod.LOGGER.error("initializeClient failed for " + item, t);
            }
        }
        CACHE.put(item, holder[0]);
        return holder[0];
    }
}
