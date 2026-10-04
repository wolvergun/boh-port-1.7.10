package net.mcreator.boh.compat.forge.items;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** Forge SlotItemHandler: a 1.7.10 Slot backed by an IItemHandler; 1.20 mayPlace/mayPickup are honoured. */
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
        return handler;
    }

    public boolean mayPlace(ItemStack stack) {
        return M.legacy(stack) != null && handler.isItemValid(index, stack);
    }

    public boolean mayPickup(EntityPlayer player) {
        return M.legacy(handler.extractItem(index, 1, true)) != null;
    }

    public void onTake(EntityPlayer player, ItemStack stack) {
        super.onPickupFromSlot(player, M.legacy(stack));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return mayPlace(M.stack(stack));
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return mayPickup(player);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        onTake(player, M.stack(stack));
    }

    @Override
    public ItemStack getStack() {
        return M.legacy(handler.getStackInSlot(index));
    }

    @Override
    public void putStack(ItemStack stack) {
        if (handler instanceof IItemHandlerModifiable) ((IItemHandlerModifiable) handler).setStackInSlot(index, M.stack(stack));
        onSlotChanged();
    }

    @Override
    public int getSlotStackLimit() {
        return handler.getSlotLimit(index);
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return M.legacy(handler.extractItem(index, amount, false));
    }

    @Override
    public boolean isSlotInInventory(net.minecraft.inventory.IInventory inv, int slot) {
        return false;
    }
}
