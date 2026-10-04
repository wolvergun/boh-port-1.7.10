package net.mcreator.boh.item;

import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.item.BohItem;

public class WerewolfTeethItem extends BohItem {

    public WerewolfTeethItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
