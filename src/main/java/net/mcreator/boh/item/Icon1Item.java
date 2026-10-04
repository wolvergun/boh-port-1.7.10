package net.mcreator.boh.item;

import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;

public class Icon1Item extends BohItem {
    public Icon1Item() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
