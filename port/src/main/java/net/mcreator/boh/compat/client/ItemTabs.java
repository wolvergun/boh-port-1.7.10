package net.mcreator.boh.compat.client;

import net.mcreator.boh.compat.mc.world.item.CreativeModeTab;
import net.mcreator.boh.compat.registry.Registration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * 1.20 tabs list their items, but 1.7.10's creative search (and its tab check) only sees items whose own creative tab
 * is set, so each item gets the first mod tab that lists it. Runs before the vanilla-tab additions, so the mod tab
 * wins, as it is where the original shows everything.
 */
public final class ItemTabs {

    private ItemTabs() {}

    public static void assign() {
        int n = 0;
        for (CreativeModeTab tab : Registration.TABS) {
            if (tab.vanilla() == null) continue;
            for (ItemStack s : tab.displayItems()) {
                Item item = s == null ? null : s.getItem();
                if (item == null || item.getCreativeTab() != null) continue;
                item.setCreativeTab(tab.vanilla());
                // an ItemBlock's tab is its block's
                if (item instanceof ItemBlock) {
                    Block b = ((ItemBlock) item).field_150939_a;
                    if (b != null && b.getCreativeTabToDisplayOn() == null) b.setCreativeTab(tab.vanilla());
                }
                n++;
            }
        }
        net.mcreator.boh.BohMod.LOGGER.info("Creative tabs: {} items assigned to the mod's {} tabs", n, Registration.TABS.size());
    }
}
