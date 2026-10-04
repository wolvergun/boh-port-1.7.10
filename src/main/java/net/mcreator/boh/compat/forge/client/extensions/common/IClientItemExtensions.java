package net.mcreator.boh.compat.forge.client.extensions.common;

import net.mcreator.boh.compat.mc.client.model.ArmPose;
import net.mcreator.boh.compat.mc.client.model.HumanoidModel;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IClientItemExtensions {
    IClientItemExtensions DEFAULT = new IClientItemExtensions() {};

    default BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return null;
    }

    default ArmPose getArmPose(EntityLivingBase entity, InteractionHand hand, ItemStack stack) {
        return null;
    }

    default HumanoidModel<?> getHumanoidArmorModel(EntityLivingBase entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
        return original;
    }

    default Object getGenericArmorModel(EntityLivingBase entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
        return this.getHumanoidArmorModel(entity, stack, slot, original);
    }
}
