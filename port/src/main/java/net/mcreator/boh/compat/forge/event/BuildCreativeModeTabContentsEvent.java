package net.mcreator.boh.compat.forge.event;

import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.world.item.CreativeModeTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.Event;

/** Forge BuildCreativeModeTabContentsEvent: vanilla tab additions go to the matching 1.7.10 tab when the item has none. */
public class BuildCreativeModeTabContentsEvent extends Event {

    private final Object tabKey;

    public BuildCreativeModeTabContentsEvent() {
        this(null);
    }

    public BuildCreativeModeTabContentsEvent(Object tabKey) {
        this.tabKey = tabKey;
    }

    public Object getTabKey() {
        return tabKey;
    }

    public void accept(Object o) {
        // creative tabs only matter on the client, and Item.getCreativeTab doesn't exist on a server
        if (!cpw.mods.fml.common.FMLCommonHandler.instance().getSide().isClient()) return;
        Item item = o instanceof Supplier ? toItem(((Supplier<?>) o).get()) : toItem(o);
        if (item == null || item.getCreativeTab() != null) return;
        item.setCreativeTab(vanillaTab(tabKey));
    }

    private static Item toItem(Object o) {
        if (o instanceof Item) return (Item) o;
        if (o instanceof Block) return Item.getItemFromBlock((Block) o);
        if (o instanceof ItemStack) return ((ItemStack) o).getItem();
        return null;
    }

    static CreativeTabs vanillaTab(Object key) {
        if (key == CreativeModeTabs.FOOD_AND_DRINKS) return CreativeTabs.tabFood;
        if (key == CreativeModeTabs.COMBAT) return CreativeTabs.tabCombat;
        if (key == CreativeModeTabs.TOOLS_AND_UTILITIES) return CreativeTabs.tabTools;
        if (key == CreativeModeTabs.INGREDIENTS) return CreativeTabs.tabMaterials;
        if (key == CreativeModeTabs.BUILDING_BLOCKS) return CreativeTabs.tabBlock;
        if (key == CreativeModeTabs.NATURAL_BLOCKS) return CreativeTabs.tabDecorations;
        return CreativeTabs.tabMisc;
    }

    public static void postAll(cpw.mods.fml.common.eventhandler.EventBus bus) {
        for (Object k : new Object[] { CreativeModeTabs.FOOD_AND_DRINKS, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT,
            CreativeModeTabs.INGREDIENTS, CreativeModeTabs.SPAWN_EGGS, CreativeModeTabs.BUILDING_BLOCKS, CreativeModeTabs.NATURAL_BLOCKS })
            bus.post(new BuildCreativeModeTabContentsEvent(k));
    }
}
