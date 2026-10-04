package net.mcreator.boh.item;

import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;

public class PretzelItem extends BohItem {
    public PretzelItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON).food(new Builder().nutrition(6).saturationMod(0.4F).build()));
    }
}
