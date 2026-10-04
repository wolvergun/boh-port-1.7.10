package net.mcreator.boh.compat.forge.items;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/** Forge ItemStackHandler. */
public class ItemStackHandler implements IItemHandlerModifiable {

    protected NonNullList<ItemStack> stacks;

    public ItemStackHandler() {
        this(1);
    }

    public ItemStackHandler(int size) {
        stacks = NonNullList.withSize(size, M.EMPTY);
    }

    public ItemStackHandler(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public void setSize(int size) {
        stacks = NonNullList.withSize(size, M.EMPTY);
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return M.stack(stacks.get(slot));
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        stacks.set(slot, M.stack(stack));
        onContentsChanged(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStack in = M.legacy(stack);
        if (in == null) return M.EMPTY;
        if (!isItemValid(slot, in)) return stack;
        ItemStack cur = M.legacy(stacks.get(slot));
        int limit = Math.min(getSlotLimit(slot), in.getMaxStackSize());
        if (cur != null) {
            if (!ItemHandlerHelper.canItemStacksStack(in, cur)) return stack;
            limit -= cur.stackSize;
        }
        if (limit <= 0) return stack;
        boolean reached = in.stackSize > limit;
        if (!simulate) {
            if (cur == null) stacks.set(slot, reached ? ItemHandlerHelper.copyStackWithSize(in, limit) : in.copy());
            else cur.stackSize += reached ? limit : in.stackSize;
            onContentsChanged(slot);
        }
        return reached ? ItemHandlerHelper.copyStackWithSize(in, in.stackSize - limit) : M.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack cur = M.legacy(stacks.get(slot));
        if (amount <= 0 || cur == null) return M.EMPTY;
        int n = Math.min(amount, cur.stackSize);
        if (simulate) return ItemHandlerHelper.copyStackWithSize(cur, n);
        if (n >= cur.stackSize) {
            stacks.set(slot, M.EMPTY);
            onContentsChanged(slot);
            return cur;
        }
        cur.stackSize -= n;
        onContentsChanged(slot);
        return ItemHandlerHelper.copyStackWithSize(cur, n);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    protected void onContentsChanged(int slot) {}

    protected void onLoad() {}

    public NBTTagCompound serializeNBT() {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack s = M.legacy(stacks.get(i));
            if (s == null) continue;
            NBTTagCompound t = new NBTTagCompound();
            t.setInteger("Slot", i);
            s.writeToNBT(t);
            list.appendTag(t);
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", list);
        nbt.setInteger("Size", stacks.size());
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        setSize(nbt.hasKey("Size") ? nbt.getInteger("Size") : stacks.size());
        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound t = list.getCompoundTagAt(i);
            int slot = t.getInteger("Slot");
            if (slot >= 0 && slot < stacks.size()) stacks.set(slot, M.stack(ItemStack.loadItemStackFromNBT(t)));
        }
        onLoad();
    }
}
