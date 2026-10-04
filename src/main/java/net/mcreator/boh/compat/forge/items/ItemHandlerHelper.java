package net.mcreator.boh.compat.forge.items;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public final class ItemHandlerHelper {
    private ItemHandlerHelper() {
    }

    public static boolean canItemStacksStack(ItemStack a, ItemStack b) {
        a = M.legacy(a);
        b = M.legacy(b);
        return a != null && b != null && a.getItem() == b.getItem() && a.getItemDamage() == b.getItemDamage() ? ItemStack.areItemStackTagsEqual(a, b) : false;
    }

    public static ItemStack copyStackWithSize(ItemStack s, int size) {
        if (size > 0 && M.legacy(s) != null) {
            ItemStack c = s.copy();
            c.stackSize = size;
            return c;
        } else {
            return M.EMPTY;
        }
    }

    public static void giveItemToPlayer(EntityPlayer p, ItemStack stack) {
        ItemStack s = M.legacy(stack);
        if (s != null) {
            if ((!p.inventory.addItemStackToInventory(s) || s.stackSize > 0) && !p.worldObj.isRemote && s.stackSize > 0) {
                p.dropPlayerItemWithRandomChoice(s, false);
            }

            p.inventoryContainer.detectAndSendChanges();
        }
    }

    public static void giveItemToPlayer(EntityPlayer p, ItemStack stack, int slot) {
        giveItemToPlayer(p, stack);
    }

    public static ItemStack insertItem(IItemHandler h, ItemStack stack, boolean simulate) {
        if (h != null && M.legacy(stack) != null) {
            for (int i = 0; i < h.getSlots(); i++) {
                stack = h.insertItem(i, stack, simulate);
                if (M.legacy(stack) == null) {
                    return M.EMPTY;
                }
            }

            return stack;
        } else {
            return stack;
        }
    }
}
