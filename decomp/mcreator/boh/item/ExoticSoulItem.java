package net.mcreator.boh.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class ExoticSoulItem extends Item {
   public ExoticSoulItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
   }
}
