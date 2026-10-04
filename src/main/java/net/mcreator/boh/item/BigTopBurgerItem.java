package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.procedures.BigTopBurgerPlayerFinishesUsingItemProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BigTopBurgerItem extends BohItem {
    public BigTopBurgerItem() {
        super(new Properties().stacksTo(16).rarity(Rarity.UNCOMMON).food(new Builder().nutrition(12).saturationMod(3.0F).meat().build()));
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 64;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, World world, EntityLivingBase entity) {
        ItemStack retval = super.finishUsingItem(itemstack, world, entity);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        BigTopBurgerPlayerFinishesUsingItemProcedure.execute(entity);
        return retval;
    }
}
