package net.mcreator.boh.compat.forge.items;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotItemHandler extends Slot {
    private static final InventoryBasic EMPTY_INV = new InventoryBasic("", false, 0);
    private final IItemHandler handler;
    private final int index;

    public SlotItemHandler(IItemHandler handler, int index, int x, int y) {
        super(EMPTY_INV, index, x, y);
        this.handler = handler;
        this.index = index;
    }

    public IItemHandler getItemHandler() {
        return this.handler;
    }

    public boolean mayPlace(ItemStack stack) {
        return M.legacy(stack) != null && this.handler.isItemValid(this.index, stack);
    }

    public boolean mayPickup(EntityPlayer player) {
        return M.legacy(this.handler.extractItem(this.index, 1, true)) != null;
    }

    public void onTake(EntityPlayer player, ItemStack stack) {
        super.onPickupFromSlot(player, M.legacy(stack));
    }

    public boolean isItemValid(ItemStack stack) {
        return this.mayPlace(M.stack(stack));
    }

    public boolean canTakeStack(EntityPlayer player) {
        return this.mayPickup(player);
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        this.onTake(player, M.stack(stack));
    }

    public ItemStack getStack() {
        return M.legacy(this.handler.getStackInSlot(this.index));
    }

    public void putStack(ItemStack stack) {
        if (this.handler instanceof IItemHandlerModifiable) {
            ((IItemHandlerModifiable)this.handler).setStackInSlot(this.index, M.stack(stack));
        }

        this.onSlotChanged();
    }

    public int getSlotStackLimit() {
        return this.handler.getSlotLimit(this.index);
    }

    public ItemStack decrStackSize(int amount) {
        return M.legacy(this.handler.extractItem(this.index, amount, false));
    }

    public boolean isSlotInInventory(IInventory inv, int slot) {
        return false;
    }
}
