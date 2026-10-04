package net.mcreator.boh.item;

import net.mcreator.boh.procedures.LiverPlayerFinishesUsingItemProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.world.World;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.M;

public class LiverItem extends BohItem {

    public LiverItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON).food(new Builder().nutrition(4).saturationMod(0.3F).alwaysEat().build()));
    }

    public ItemStack finishUsingItem(ItemStack itemstack, World world, EntityLivingBase entity) {
        ItemStack retval = super.finishUsingItem(itemstack, world, entity);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        LiverPlayerFinishesUsingItemProcedure.execute(entity);
        return retval;
    }
}
