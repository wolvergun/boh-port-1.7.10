package net.mcreator.boh.item;

import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.minecraft.item.ItemStack;

public class FriedGillItem extends BohItem {
    public FriedGillItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON).food(new Builder().nutrition(5).saturationMod(0.4F).meat().build()));
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 0;
    }
}
