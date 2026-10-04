package net.mcreator.boh.item;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class ApplalypseItem extends Item {
   public ApplalypseItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.EPIC).food(new Builder().nutrition(4).saturationMod(0.3F).build()));
   }
}
