package net.mcreator.boh.compat.client;

import java.util.HashMap;
import java.util.Map;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.item.BohBlockItem;
import net.mcreator.boh.compat.item.BohItem;
import net.minecraft.item.Item;

public final class ItemExtensions {
    private static final Map<Item, IClientItemExtensions> CACHE = new HashMap<>();

    private ItemExtensions() {
    }

    public static IClientItemExtensions of(Item item) {
        IClientItemExtensions e = CACHE.get(item);
        if (e != null) {
            return e;
        } else {
            IClientItemExtensions[] holder = new IClientItemExtensions[]{IClientItemExtensions.DEFAULT};
            if (item instanceof BohItem || item instanceof BohBlockItem) {
                try {
                    if (item instanceof BohItem) {
                        ((BohItem)item).initializeClient(x -> holder[0] = x);
                    } else {
                        ((BohBlockItem)item).initializeClient(x -> holder[0] = x);
                    }
                } catch (Throwable var4) {
                    BohMod.LOGGER.error("initializeClient failed for " + item, var4);
                }
            }

            CACHE.put(item, holder[0]);
            return holder[0];
        }
    }
}
