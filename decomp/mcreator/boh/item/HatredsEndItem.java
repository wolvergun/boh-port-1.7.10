package net.mcreator.boh.item;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.HatredsEndToolInHandTickProcedure;
import net.mcreator.boh.procedures.KillerKnifeLivingEntityIsHitWithToolProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class HatredsEndItem extends SwordItem {
   public HatredsEndItem() {
      super(new Tier() {
         public int getUses() {
            return 999;
         }

         public float getSpeed() {
            return 6.0F;
         }

         public float getAttackDamageBonus() {
            return 4.0F;
         }

         public int getLevel() {
            return 1;
         }

         public int getEnchantmentValue() {
            return 0;
         }

         public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack[]{new ItemStack((ItemLike)BohModItems.KILLERS_SOUL.get())});
         }
      }, 3, -1.5F, new Properties());
   }

   public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
      KillerKnifeLivingEntityIsHitWithToolProcedure.execute(entity.level(), entity, sourceentity);
      return retval;
   }

   public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
      super.inventoryTick(itemstack, world, entity, slot, selected);
      if (selected) {
         HatredsEndToolInHandTickProcedure.execute(entity);
      }
   }
}
