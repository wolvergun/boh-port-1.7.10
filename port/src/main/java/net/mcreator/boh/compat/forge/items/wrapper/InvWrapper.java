package net.mcreator.boh.compat.forge.items.wrapper;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.items.IItemHandlerModifiable;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/** Forge InvWrapper over a 1.7.10 IInventory. */
public class InvWrapper implements IItemHandlerModifiable {

    protected final IInventory inv;

    public InvWrapper(IInventory inv) {
        this.inv = inv;
    }

    public IInventory getInv() {
        return inv;
    }

    @Override
    public int getSlots() {
        return inv.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return M.stack(inv.getStackInSlot(slot));
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        inv.setInventorySlotContents(slot, M.legacy(stack));
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStack in = M.legacy(stack);
        if (in == null) return M.EMPTY;
        if (!inv.isItemValidForSlot(slot, in)) return stack;
        ItemStack cur = inv.getStackInSlot(slot);
        int limit = Math.min(getSlotLimit(slot), in.getMaxStackSize());
        if (cur != null) {
            if (!ItemHandlerHelper.canItemStacksStack(in, cur)) return stack;
            limit -= cur.stackSize;
        }
        if (limit <= 0) return stack;
        int n = Math.min(limit, in.stackSize);
        if (!simulate) {
            if (cur == null) inv.setInventorySlotContents(slot, ItemHandlerHelper.copyStackWithSize(in, n));
            else cur.stackSize += n;
            inv.markDirty();
        }
        return n >= in.stackSize ? M.EMPTY : ItemHandlerHelper.copyStackWithSize(in, in.stackSize - n);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack cur = inv.getStackInSlot(slot);
        if (amount <= 0 || cur == null) return M.EMPTY;
        int n = Math.min(amount, cur.stackSize);
        if (simulate) return ItemHandlerHelper.copyStackWithSize(cur, n);
        ItemStack out = inv.decrStackSize(slot, n);
        inv.markDirty();
        return M.stack(out);
    }

    @Override
    public int getSlotLimit(int slot) {
        return inv.getInventoryStackLimit();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return inv.isItemValidForSlot(slot, M.legacy(stack));
    }
}
