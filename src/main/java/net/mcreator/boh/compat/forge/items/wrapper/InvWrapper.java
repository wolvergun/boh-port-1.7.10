package net.mcreator.boh.compat.forge.items.wrapper;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.items.IItemHandlerModifiable;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InvWrapper implements IItemHandlerModifiable {
    protected final IInventory inv;

    public InvWrapper(IInventory inv) {
        this.inv = inv;
    }

    public IInventory getInv() {
        return this.inv;
    }

    @Override
    public int getSlots() {
        return this.inv.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return M.stack(this.inv.getStackInSlot(slot));
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.inv.setInventorySlotContents(slot, M.legacy(stack));
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStack in = M.legacy(stack);
        if (in == null) {
            return M.EMPTY;
        } else if (!this.inv.isItemValidForSlot(slot, in)) {
            return stack;
        } else {
            ItemStack cur = this.inv.getStackInSlot(slot);
            int limit = Math.min(this.getSlotLimit(slot), in.getMaxStackSize());
            if (cur != null) {
                if (!ItemHandlerHelper.canItemStacksStack(in, cur)) {
                    return stack;
                }

                limit -= cur.stackSize;
            }

            if (limit <= 0) {
                return stack;
            } else {
                int n = Math.min(limit, in.stackSize);
                if (!simulate) {
                    if (cur == null) {
                        this.inv.setInventorySlotContents(slot, ItemHandlerHelper.copyStackWithSize(in, n));
                    } else {
                        cur.stackSize += n;
                    }

                    this.inv.markDirty();
                }

                return n >= in.stackSize ? M.EMPTY : ItemHandlerHelper.copyStackWithSize(in, in.stackSize - n);
            }
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack cur = this.inv.getStackInSlot(slot);
        if (amount > 0 && cur != null) {
            int n = Math.min(amount, cur.stackSize);
            if (simulate) {
                return ItemHandlerHelper.copyStackWithSize(cur, n);
            } else {
                ItemStack out = this.inv.decrStackSize(slot, n);
                this.inv.markDirty();
                return M.stack(out);
            }
        } else {
            return M.EMPTY;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.inv.getInventoryStackLimit();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.inv.isItemValidForSlot(slot, M.legacy(stack));
    }
}
