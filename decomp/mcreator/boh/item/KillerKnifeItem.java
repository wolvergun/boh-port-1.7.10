package net.mcreator.boh.item;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.KillerKnifeLivingEntityIsHitWithToolProcedure;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class KillerKnifeItem extends SwordItem {
   public KillerKnifeItem() {
      super(new Tier() {
         public int getUses() {
            return 666;
         }

         public float getSpeed() {
            return 4.0F;
         }

         public float getAttackDamageBonus() {
            return -1.0F;
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
}
