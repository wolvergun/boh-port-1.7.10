package net.mcreator.boh.item;

import net.mcreator.boh.procedures.HiltOfABlacksmithToolInInventoryTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class HiltOfABlacksmithItem extends SwordItem {
   public HiltOfABlacksmithItem() {
      super(new Tier() {
         public int getUses() {
            return 666;
         }

         public float getSpeed() {
            return 7.0F;
         }

         public float getAttackDamageBonus() {
            return 6.0F;
         }

         public int getLevel() {
            return 3;
         }

         public int getEnchantmentValue() {
            return 2;
         }

         public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack[]{new ItemStack(Blocks.OBSIDIAN)});
         }
      }, 3, -3.0F, new Properties());
   }

   public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
      super.inventoryTick(itemstack, world, entity, slot, selected);
      HiltOfABlacksmithToolInInventoryTickProcedure.execute(entity);
   }
}
