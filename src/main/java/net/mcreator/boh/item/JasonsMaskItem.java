package net.mcreator.boh.item;

import com.google.common.collect.Iterables;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.item.ArmorItem;
import net.mcreator.boh.compat.mc.world.item.ArmorMaterial;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Type;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.procedures.JasonsMaskHelmetTickEventProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class JasonsMaskItem extends ArmorItem {
    public JasonsMaskItem(Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{13, 15, 16, 11}[M.getIndex(M.getSlot(type))] * 15;
            }

            @Override
            public int getDefenseForType(Type type) {
                return new int[]{0, 5, 0, 5}[M.getIndex(M.getSlot(type))];
            }

            @Override
            public int getEnchantmentValue() {
                return 9;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            @Override
            public String getName() {
                return "jasons_mask";
            }

            @Override
            public float getToughness() {
                return 0.0F;
            }

            @Override
            public float getKnockbackResistance() {
                return 0.5F;
            }
        }, type, properties);
    }

    public static class Helmet extends JasonsMaskItem {
        public Helmet() {
            super(Type.HELMET, new Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "boh:textures/models/armor/jason_mask_layer_1.png";
        }

        @Override
        public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
            super.inventoryTick(itemstack, world, entity, slot, selected);
            if (entity instanceof EntityPlayer player && Iterables.contains(M.getArmorSlots(player), itemstack)) {
                JasonsMaskHelmetTickEventProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity);
            }
        }
    }
}
