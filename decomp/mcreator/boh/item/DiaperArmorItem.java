package net.mcreator.boh.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;

public abstract class DiaperArmorItem extends ArmorItem {
   public DiaperArmorItem(Type type, Properties properties) {
      super(
         new ArmorMaterial() {
            public int getDurabilityForType(Type type) {
               return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 6;
            }

            public int getDefenseForType(Type type) {
               return new int[]{1, 2, 2, 1}[type.getSlot().getIndex()];
            }

            public int getEnchantmentValue() {
               return 4;
            }

            public SoundEvent getEquipSound() {
               return SoundEvents.EMPTY;
            }

            public Ingredient getRepairIngredient() {
               return CompoundIngredient.of(
                  new Ingredient[]{
                     Ingredient.of(new ItemStack[]{new ItemStack(Items.PAPER)}),
                     Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:wool")))
                  }
               );
            }

            public String getName() {
               return "diaper_armor";
            }

            public float getToughness() {
               return 0.0F;
            }

            public float getKnockbackResistance() {
               return 0.0F;
            }
         },
         type,
         properties
      );
   }

   public static class Leggings extends DiaperArmorItem {
      public Leggings() {
         super(Type.LEGGINGS, new Properties());
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "boh:textures/models/armor/diaper_layer_2.png";
      }
   }
}
