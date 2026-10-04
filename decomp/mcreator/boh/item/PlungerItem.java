package net.mcreator.boh.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;

public class PlungerItem extends SwordItem {
   public PlungerItem() {
      super(new Tier() {
         public int getUses() {
            return 750;
         }

         public float getSpeed() {
            return 4.0F;
         }

         public float getAttackDamageBonus() {
            return 2.5F;
         }

         public int getLevel() {
            return 2;
         }

         public int getEnchantmentValue() {
            return 14;
         }

         public Ingredient getRepairIngredient() {
            return Ingredient.of();
         }
      }, 3, -2.8F, new Properties());
   }
}
