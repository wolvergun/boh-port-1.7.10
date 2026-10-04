package net.mcreator.boh.item;

import net.mcreator.boh.init.BohModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class SoulStealerItem extends SwordItem {
   public SoulStealerItem() {
      super(
         new Tier() {
            public int getUses() {
               return 25;
            }

            public float getSpeed() {
               return 4.0F;
            }

            public float getAttackDamageBonus() {
               return -2.0F;
            }

            public int getLevel() {
               return 2;
            }

            public int getEnchantmentValue() {
               return 2;
            }

            public Ingredient getRepairIngredient() {
               return Ingredient.of(
                  new ItemStack[]{
                     new ItemStack((ItemLike)BohModItems.EXOTIC_SOUL.get()),
                     new ItemStack((ItemLike)BohModItems.DEMONIC_SOUL.get()),
                     new ItemStack((ItemLike)BohModItems.KILLERS_SOUL.get()),
                     new ItemStack((ItemLike)BohModItems.MONSTROUS_SOUL.get())
                  }
               );
            }
         },
         3,
         -2.7F,
         new Properties()
      );
   }
}
