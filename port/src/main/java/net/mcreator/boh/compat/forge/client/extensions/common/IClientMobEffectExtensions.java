package net.mcreator.boh.compat.forge.client.extensions.common;

import net.minecraft.potion.PotionEffect;

/** 1.20 IClientMobEffectExtensions: only the visibility flags are honoured. */
public interface IClientMobEffectExtensions {

    default boolean isVisibleInInventory(PotionEffect effect) {
        return true;
    }

    default boolean isVisibleInGui(PotionEffect effect) {
        return true;
    }

    default boolean renderInventoryText(PotionEffect instance, net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen<?> screen, net.mcreator.boh.compat.mc.client.gui.GuiGraphics guiGraphics, int x, int y, int blitOffset) {
        return false;
    }

    default boolean renderInventoryIcon(PotionEffect instance, net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen<?> screen, net.mcreator.boh.compat.mc.client.gui.GuiGraphics guiGraphics, int x, int y, int blitOffset) {
        return false;
    }

    default boolean renderGuiIcon(PotionEffect instance, Object gui, net.mcreator.boh.compat.mc.client.gui.GuiGraphics guiGraphics, int x, int y, float z, float alpha) {
        return false;
    }
}
