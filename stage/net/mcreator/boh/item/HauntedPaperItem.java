package net.mcreator.boh.item;

import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.item.BohItem;

public class HauntedPaperItem extends BohItem {

    public HauntedPaperItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    }
}
