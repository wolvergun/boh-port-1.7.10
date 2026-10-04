package net.mcreator.boh.item;

import net.mcreator.boh.compat.mc.world.food.Builder;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.item.BohItem;

public class WalnutItem extends BohItem {

    public WalnutItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON).food(new Builder().nutrition(1).saturationMod(0.1F).build()));
    }

    public int getUseDuration(ItemStack itemstack) {
        return 8;
    }
}
