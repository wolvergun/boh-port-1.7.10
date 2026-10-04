package net.mcreator.boh.compat.mc.world;

import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public interface MenuProvider {
    Component getDisplayName();

    Container createMenu(int var1, InventoryPlayer var2, EntityPlayer var3);
}
