package net.mcreator.boh.compat.mc.world.inventory;

import java.util.List;

import net.mcreator.boh.compat.M;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** 1.20 AbstractContainerMenu on 1.7.10 Container. */
public abstract class AbstractContainerMenu extends Container {

    public final List<Slot> slots;
    public int containerId;
    private final MenuType<?> menuType;

    @SuppressWarnings("unchecked")
    protected AbstractContainerMenu(MenuType<?> type, int id) {
        menuType = type;
        containerId = id;
        windowId = id;
        slots = inventorySlots;
    }

    public MenuType<?> getType() {
        return menuType;
    }

    public Slot addSlot(Slot s) {
        return addSlotToContainer(s);
    }

    public abstract boolean stillValid(EntityPlayer player);

    public ItemStack quickMoveStack(EntityPlayer player, int index) {
        return M.EMPTY;
    }

    public void removed(EntityPlayer player) {
        super.onContainerClosed(player);
    }

    public void broadcastChanges() {
        detectAndSendChanges();
    }

    protected boolean moveItemStackTo(ItemStack stack, int start, int end, boolean reverse) {
        ItemStack s = M.legacy(stack);
        return s != null && mergeItemStack(s, start, end, reverse);
    }

    public static int getRedstoneSignalFromContainer(net.minecraft.inventory.IInventory inv) {
        return Container.calcRedstoneFromInventory(inv);
    }

    public static boolean stillValid(ContainerLevelAccess access, EntityPlayer player, Block block) {
        if (access == null || access.world == null) return true;
        return access.world.getBlock(access.pos.getX(), access.pos.getY(), access.pos.getZ()) == block
            && player.getDistanceSq(access.pos.getX() + 0.5, access.pos.getY() + 0.5, access.pos.getZ() + 0.5) <= 64;
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return stillValid(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return M.legacy(quickMoveStack(player, index));
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        removed(player);
    }
}
