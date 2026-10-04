package net.mcreator.boh.item;

import net.mcreator.boh.procedures.BigTopBurgerPlayerFinishesUsingItemProcedure;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class BigTopBurgerItem extends Item {
   public BigTopBurgerItem() {
      super(new Properties().stacksTo(16).rarity(Rarity.UNCOMMON).food(new Builder().nutrition(12).saturationMod(3.0F).meat().build()));
   }

   public int getUseDuration(ItemStack itemstack) {
      return 64;
   }

   public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
      ItemStack retval = super.finishUsingItem(itemstack, world, entity);
      double x = entity.getX();
      double y = entity.getY();
      double z = entity.getZ();
      BigTopBurgerPlayerFinishesUsingItemProcedure.execute(entity);
      return retval;
   }
}
