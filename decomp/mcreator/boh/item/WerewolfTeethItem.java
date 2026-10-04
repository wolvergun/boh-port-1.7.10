package net.mcreator.boh.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class WerewolfTeethItem extends Item {
   public WerewolfTeethItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
   }
}
