package net.mcreator.boh.compat.forge.items;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Forge ItemHandlerHelper. */
public final class ItemHandlerHelper {

    private ItemHandlerHelper() {}

    public static boolean canItemStacksStack(ItemStack a, ItemStack b) {
        a = M.legacy(a);
        b = M.legacy(b);
        if (a == null || b == null || a.getItem() != b.getItem() || a.getItemDamage() != b.getItemDamage()) return false;
        return ItemStack.areItemStackTagsEqual(a, b);
    }

    public static ItemStack copyStackWithSize(ItemStack s, int size) {
        if (size <= 0 || M.legacy(s) == null) return M.EMPTY;
        ItemStack c = s.copy();
        c.stackSize = size;
        return c;
    }

    public static void giveItemToPlayer(EntityPlayer p, ItemStack stack) {
        ItemStack s = M.legacy(stack);
        if (s == null) return;
        if (!p.inventory.addItemStackToInventory(s) || s.stackSize > 0) {
            if (!p.worldObj.isRemote && s.stackSize > 0) p.dropPlayerItemWithRandomChoice(s, false);
        }
        p.inventoryContainer.detectAndSendChanges();
    }

    public static void giveItemToPlayer(EntityPlayer p, ItemStack stack, int slot) {
        giveItemToPlayer(p, stack);
    }

    public static ItemStack insertItem(IItemHandler h, ItemStack stack, boolean simulate) {
        if (h == null || M.legacy(stack) == null) return stack;
        for (int i = 0; i < h.getSlots(); i++) {
            stack = h.insertItem(i, stack, simulate);
            if (M.legacy(stack) == null) return M.EMPTY;
        }
        return stack;
    }
}
