package net.mcreator.boh.enchantment;

import java.util.List;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.level.ItemLike;

public class RendEnchantment extends Enchantment {
   private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create(
      "boh_rend",
      item -> Ingredient.of(
            new ItemStack[]{
               new ItemStack((ItemLike)BohModItems.KILLER_KNIFE.get()),
               new ItemStack((ItemLike)BohModItems.THE_GREAT_KNIFE.get()),
               new ItemStack((ItemLike)BohModItems.SCHIZOSLEDGE.get()),
               new ItemStack((ItemLike)BohModItems.THE_SLASHER.get()),
               new ItemStack((ItemLike)BohModItems.MASSACRE_AXE.get()),
               new ItemStack((ItemLike)BohModItems.DESIRE_EDGE.get()),
               new ItemStack((ItemLike)BohModItems.TACTICAL_KNIFE.get()),
               new ItemStack((ItemLike)BohModItems.PAINTED_SWORD.get()),
               new ItemStack((ItemLike)BohModItems.MACHETE.get()),
               new ItemStack((ItemLike)BohModItems.GIANT_SCISSOR.get()),
               new ItemStack((ItemLike)BohModItems.SOUL_STEALER.get()),
               new ItemStack((ItemLike)BohModItems.HILT_OF_A_BLACKSMITH.get()),
               new ItemStack((ItemLike)BohModItems.ZACKS_SCYTHE.get()),
               new ItemStack(Items.WOODEN_SWORD),
               new ItemStack(Items.STONE_SWORD),
               new ItemStack(Items.IRON_SWORD),
               new ItemStack(Items.GOLDEN_SWORD),
               new ItemStack(Items.DIAMOND_SWORD),
               new ItemStack(Items.NETHERITE_SWORD)
            }
         )
         .test(new ItemStack(item))
   );

   public RendEnchantment() {
      super(Rarity.COMMON, ENCHANTMENT_CATEGORY, EquipmentSlot.values());
   }

   public int getMinCost(int level) {
      return 1 + level * 10;
   }

   public int getMaxCost(int level) {
      return 6 + level * 10;
   }

   protected boolean checkCompatibility(Enchantment enchantment) {
      return super.checkCompatibility(enchantment) && !List.of(Enchantments.SHARPNESS, Enchantments.BANE_OF_ARTHROPODS, Enchantments.SWEEPING_EDGE).contains(enchantment);
   }

   public boolean isTreasureOnly() {
      return true;
   }
}
