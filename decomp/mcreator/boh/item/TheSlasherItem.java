package net.mcreator.boh.item;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.MassacreAxeLivingEntityIsHitWithToolProcedure;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class TheSlasherItem extends SwordItem {
   public TheSlasherItem() {
      super(new Tier() {
         public int getUses() {
            return 250;
         }

         public float getSpeed() {
            return 4.0F;
         }

         public float getAttackDamageBonus() {
            return 2.5F;
         }

         public int getLevel() {
            return 1;
         }

         public int getEnchantmentValue() {
            return 15;
         }

         public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack[]{new ItemStack((ItemLike)BohModItems.KILLERS_SOUL.get())});
         }
      }, 3, -3.1F, new Properties());
   }

   public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
      MassacreAxeLivingEntityIsHitWithToolProcedure.execute(entity);
      return retval;
   }
}
