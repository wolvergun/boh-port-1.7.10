package net.mcreator.boh.item;

import com.google.common.collect.Iterables;
import net.mcreator.boh.procedures.JasonsMaskHelmetTickEventProcedure;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.item.ArmorItem;
import net.mcreator.boh.compat.mc.world.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Type;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public abstract class JasonsMaskItem extends ArmorItem {

    public JasonsMaskItem(Type type, Properties properties) {
        super(new ArmorMaterial() {

            public int getDurabilityForType(Type type) {
                return new int[] { 13, 15, 16, 11 }[M.getIndex(M.getSlot(type))] * 15;
            }

            public int getDefenseForType(Type type) {
                return new int[] { 0, 5, 0, 5 }[M.getIndex(M.getSlot(type))];
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "jasons_mask";
            }

            public float getToughness() {
                return 0.0F;
            }

            public float getKnockbackResistance() {
                return 0.5F;
            }
        }, type, properties);
    }

    public static class Helmet extends JasonsMaskItem {

        public Helmet() {
            super(Type.HELMET, new Properties());
        }

        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "boh:textures/models/armor/jason_mask_layer_1.png";
        }

        public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
            super.inventoryTick(itemstack, world, entity, slot, selected);
            if (entity instanceof EntityPlayer player && Iterables.contains(M.getArmorSlots(player), itemstack)) {
                JasonsMaskHelmetTickEventProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity);
            }
        }
    }
}
