package net.mcreator.boh.item;

import net.mcreator.boh.compat.mc.world.food.Builder;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.item.BohItem;

public class FriedGillItem extends BohItem {

    public FriedGillItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON).food(new Builder().nutrition(5).saturationMod(0.4F).meat().build()));
    }

    public int getUseDuration(ItemStack itemstack) {
        return 0;
    }
}
