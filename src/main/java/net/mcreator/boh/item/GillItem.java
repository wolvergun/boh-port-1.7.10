package net.mcreator.boh.item;

import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.UseAnim;
import net.minecraft.item.ItemStack;

public class GillItem extends BohItem {
    public GillItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON).food(new Builder().nutrition(2).saturationMod(0.1F).build()));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 0;
    }
}
