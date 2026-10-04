package net.mcreator.boh.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class SpinelItem extends Item {
   public SpinelItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.RARE));
   }
}
