package net.mcreator.boh.compat.forge.items;

import net.minecraft.item.ItemStack;

public interface IItemHandlerModifiable extends IItemHandler {
    void setStackInSlot(int var1, ItemStack var2);
}
