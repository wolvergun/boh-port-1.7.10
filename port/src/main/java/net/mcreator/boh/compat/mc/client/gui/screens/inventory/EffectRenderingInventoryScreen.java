package net.mcreator.boh.compat.mc.client.gui.screens.inventory;

import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.minecraft.entity.player.InventoryPlayer;

/** 1.20 EffectRenderingInventoryScreen (type used in mob effect client extensions). */
public abstract class EffectRenderingInventoryScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public EffectRenderingInventoryScreen(T menu, InventoryPlayer inv, Component title) {
        super(menu, inv, title);
    }
}
