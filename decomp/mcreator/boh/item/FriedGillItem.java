package net.mcreator.boh.item;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class FriedGillItem extends Item {
   public FriedGillItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON).food(new Builder().nutrition(5).saturationMod(0.4F).meat().build()));
   }

   public int getUseDuration(ItemStack itemstack) {
      return 0;
   }
}
