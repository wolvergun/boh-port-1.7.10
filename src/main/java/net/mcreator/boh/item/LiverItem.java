package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.procedures.LiverPlayerFinishesUsingItemProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LiverItem extends BohItem {
    public LiverItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON).food(new Builder().nutrition(4).saturationMod(0.3F).alwaysEat().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, World world, EntityLivingBase entity) {
        ItemStack retval = super.finishUsingItem(itemstack, world, entity);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        LiverPlayerFinishesUsingItemProcedure.execute(entity);
        return retval;
    }
}
