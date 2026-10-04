package net.mcreator.boh.compat.forge.client.extensions.common;

import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.potion.PotionEffect;

public interface IClientMobEffectExtensions {
    default boolean isVisibleInInventory(PotionEffect effect) {
        return true;
    }

    default boolean isVisibleInGui(PotionEffect effect) {
        return true;
    }

    default boolean renderInventoryText(PotionEffect instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
        return false;
    }

    default boolean renderInventoryIcon(PotionEffect instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
        return false;
    }

    default boolean renderGuiIcon(PotionEffect instance, Object gui, GuiGraphics guiGraphics, int x, int y, float z, float alpha) {
        return false;
    }
}
