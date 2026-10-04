package net.mcreator.boh.compat.forge.items;

import net.minecraft.item.ItemStack;

/** Forge IItemHandler (empty stacks are M.EMPTY, never null). */
public interface IItemHandler {

    int getSlots();

    ItemStack getStackInSlot(int slot);

    ItemStack insertItem(int slot, ItemStack stack, boolean simulate);

    ItemStack extractItem(int slot, int amount, boolean simulate);

    int getSlotLimit(int slot);

    boolean isItemValid(int slot, ItemStack stack);
}
