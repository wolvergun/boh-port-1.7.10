package net.mcreator.boh.item;

import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundEvents;
import net.mcreator.boh.compat.mc.tags.ItemTags;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.item.ArmorItem;
import net.mcreator.boh.compat.mc.world.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.item.Type;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.forge.common.crafting.CompoundIngredient;
import net.mcreator.boh.compat.M;

public abstract class DiaperArmorItem extends ArmorItem {

    public DiaperArmorItem(Type type, Properties properties) {
        super(new ArmorMaterial() {

            public int getDurabilityForType(Type type) {
                return new int[] { 13, 15, 16, 11 }[M.getIndex(M.getSlot(type))] * 6;
            }

            public int getDefenseForType(Type type) {
                return new int[] { 1, 2, 2, 1 }[M.getIndex(M.getSlot(type))];
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public SoundEvent getEquipSound() {
                return SoundEvents.EMPTY;
            }

            public Ingredient getRepairIngredient() {
                return CompoundIngredient.of(new Ingredient[] { Ingredient.of(new ItemStack[] { M.new_ItemStack(Items.PAPER) }), Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:wool"))) });
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
        }, type, properties);
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
