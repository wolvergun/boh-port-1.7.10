package net.mcreator.boh.item;

import net.mcreator.boh.procedures.LifeformEffigyItemInHandTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class LifeformEffigyItem extends Item {
   public LifeformEffigyItem() {
      super(new Properties().stacksTo(1).rarity(Rarity.RARE));
   }

   public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
      super.inventoryTick(itemstack, world, entity, slot, selected);
      LifeformEffigyItemInHandTickProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
   }
}
