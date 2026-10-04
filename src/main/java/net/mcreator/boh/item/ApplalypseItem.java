package net.mcreator.boh.item;

import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;

public class ApplalypseItem extends BohItem {
    public ApplalypseItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.EPIC).food(new Builder().nutrition(4).saturationMod(0.3F).build()));
    }
}
