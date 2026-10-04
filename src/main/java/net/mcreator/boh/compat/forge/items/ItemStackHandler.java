package net.mcreator.boh.compat.forge.items;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemStackHandler implements IItemHandlerModifiable {
    protected NonNullList<ItemStack> stacks;

    public ItemStackHandler() {
        this(1);
    }

    public ItemStackHandler(int size) {
        this.stacks = NonNullList.withSize(size, M.EMPTY);
    }

    public ItemStackHandler(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public void setSize(int size) {
        this.stacks = NonNullList.withSize(size, M.EMPTY);
    }

    @Override
    public int getSlots() {
        return this.stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return M.stack(this.stacks.get(slot));
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.stacks.set(slot, M.stack(stack));
        this.onContentsChanged(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStack in = M.legacy(stack);
        if (in == null) {
            return M.EMPTY;
        } else if (!this.isItemValid(slot, in)) {
            return stack;
        } else {
            ItemStack cur = M.legacy(this.stacks.get(slot));
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
                boolean reached = in.stackSize > limit;
                if (!simulate) {
                    if (cur == null) {
                        this.stacks.set(slot, reached ? ItemHandlerHelper.copyStackWithSize(in, limit) : in.copy());
                    } else {
                        cur.stackSize = cur.stackSize + (reached ? limit : in.stackSize);
                    }

                    this.onContentsChanged(slot);
                }

                return reached ? ItemHandlerHelper.copyStackWithSize(in, in.stackSize - limit) : M.EMPTY;
            }
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack cur = M.legacy(this.stacks.get(slot));
        if (amount > 0 && cur != null) {
            int n = Math.min(amount, cur.stackSize);
            if (simulate) {
                return ItemHandlerHelper.copyStackWithSize(cur, n);
            } else if (n >= cur.stackSize) {
                this.stacks.set(slot, M.EMPTY);
                this.onContentsChanged(slot);
                return cur;
            } else {
                cur.stackSize -= n;
                this.onContentsChanged(slot);
                return ItemHandlerHelper.copyStackWithSize(cur, n);
            }
        } else {
            return M.EMPTY;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    protected void onContentsChanged(int slot) {
    }

    protected void onLoad() {
    }

    public NBTTagCompound serializeNBT() {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack s = M.legacy(this.stacks.get(i));
            if (s != null) {
                NBTTagCompound t = new NBTTagCompound();
                t.setInteger("Slot", i);
                s.writeToNBT(t);
                list.appendTag(t);
            }
        }

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", list);
        nbt.setInteger("Size", this.stacks.size());
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        this.setSize(nbt.hasKey("Size") ? nbt.getInteger("Size") : this.stacks.size());
        NBTTagList list = nbt.getTagList("Items", 10);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound t = list.getCompoundTagAt(i);
            int slot = t.getInteger("Slot");
            if (slot >= 0 && slot < this.stacks.size()) {
                this.stacks.set(slot, M.stack(ItemStack.loadItemStackFromNBT(t)));
            }
        }

        this.onLoad();
    }
}
