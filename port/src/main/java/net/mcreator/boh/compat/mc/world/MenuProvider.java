package net.mcreator.boh.compat.mc.world;

import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/** 1.20 MenuProvider. */
public interface MenuProvider {

    Component getDisplayName();

    Container createMenu(int id, InventoryPlayer inventory, EntityPlayer player);
}
