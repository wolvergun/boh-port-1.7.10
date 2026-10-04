package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BowlCreepypastaItem extends BohItem {
    public BowlCreepypastaItem() {
        super(new Properties().stacksTo(16).rarity(Rarity.COMMON).food(new Builder().nutrition(7).saturationMod(0.6F).meat().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, World world, EntityLivingBase entity) {
        ItemStack retval = M.new_ItemStack(Items.BOWL);
        super.finishUsingItem(itemstack, world, entity);
        if (M.isEmpty(itemstack)) {
            return retval;
        } else {
            if (entity instanceof EntityPlayer player && !M.instabuild(M.getAbilities(player)) && !M.add(M.getInventory(player), retval)) {
                M.drop(player, retval, false);
            }

            return itemstack;
        }
    }
}
