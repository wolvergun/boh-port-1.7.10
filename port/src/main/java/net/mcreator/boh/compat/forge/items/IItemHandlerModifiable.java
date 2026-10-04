package net.mcreator.boh.compat.forge.items;

import net.minecraft.item.ItemStack;

/** Forge IItemHandlerModifiable. */
public interface IItemHandlerModifiable extends IItemHandler {

    void setStackInSlot(int slot, ItemStack stack);
}
