package net.mcreator.boh.compat.forge.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventBus;
import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.world.item.CreativeModeTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BuildCreativeModeTabContentsEvent extends Event {
    private final Object tabKey;

    public BuildCreativeModeTabContentsEvent() {
        this(null);
    }

    public BuildCreativeModeTabContentsEvent(Object tabKey) {
        this.tabKey = tabKey;
    }

    public Object getTabKey() {
        return this.tabKey;
    }

    public void accept(Object o) {
        Item item = o instanceof Supplier ? toItem(((Supplier)o).get()) : toItem(o);
        if (item != null && item.getCreativeTab() == null) {
            item.setCreativeTab(vanillaTab(this.tabKey));
        }
    }

    private static Item toItem(Object o) {
        if (o instanceof Item) {
            return (Item)o;
        } else if (o instanceof Block) {
            return Item.getItemFromBlock((Block)o);
        } else {
            return o instanceof ItemStack ? ((ItemStack)o).getItem() : null;
        }
    }

    static CreativeTabs vanillaTab(Object key) {
        if (key == CreativeModeTabs.FOOD_AND_DRINKS) {
            return CreativeTabs.tabFood;
        } else if (key == CreativeModeTabs.COMBAT) {
            return CreativeTabs.tabCombat;
        } else if (key == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            return CreativeTabs.tabTools;
        } else if (key == CreativeModeTabs.INGREDIENTS) {
            return CreativeTabs.tabMaterials;
        } else if (key == CreativeModeTabs.BUILDING_BLOCKS) {
            return CreativeTabs.tabBlock;
        } else {
            return key == CreativeModeTabs.NATURAL_BLOCKS ? CreativeTabs.tabDecorations : CreativeTabs.tabMisc;
        }
    }

    public static void postAll(EventBus bus) {
        for (Object k : new Object[]{
            CreativeModeTabs.FOOD_AND_DRINKS,
            CreativeModeTabs.TOOLS_AND_UTILITIES,
            CreativeModeTabs.COMBAT,
            CreativeModeTabs.INGREDIENTS,
            CreativeModeTabs.SPAWN_EGGS,
            CreativeModeTabs.BUILDING_BLOCKS,
            CreativeModeTabs.NATURAL_BLOCKS
        }) {
            bus.post(new BuildCreativeModeTabContentsEvent(k));
        }
    }
}
