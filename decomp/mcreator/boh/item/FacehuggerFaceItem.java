package net.mcreator.boh.item;

import net.mcreator.boh.procedures.FacehuggerFaceItemInInventoryTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class FacehuggerFaceItem extends Item {
   public FacehuggerFaceItem() {
      super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
   }

   public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
      super.inventoryTick(itemstack, world, entity, slot, selected);
      FacehuggerFaceItemInInventoryTickProcedure.execute(world, entity, itemstack);
   }
}
