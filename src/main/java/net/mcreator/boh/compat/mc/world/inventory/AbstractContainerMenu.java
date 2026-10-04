package net.mcreator.boh.compat.mc.world.inventory;

import java.util.List;
import net.mcreator.boh.compat.M;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AbstractContainerMenu extends Container {
    public final List<Slot> slots;
    public int containerId;
    private final MenuType<?> menuType;

    protected AbstractContainerMenu(MenuType<?> type, int id) {
        this.menuType = type;
        this.containerId = id;
        this.windowId = id;
        this.slots = this.inventorySlots;
    }

    public MenuType<?> getType() {
        return this.menuType;
    }

    public Slot addSlot(Slot s) {
        return this.addSlotToContainer(s);
    }

    public abstract boolean stillValid(EntityPlayer var1);

    public ItemStack quickMoveStack(EntityPlayer player, int index) {
        return M.EMPTY;
    }

    public void removed(EntityPlayer player) {
        super.onContainerClosed(player);
    }

    public void broadcastChanges() {
        this.detectAndSendChanges();
    }

    protected boolean moveItemStackTo(ItemStack stack, int start, int end, boolean reverse) {
        ItemStack s = M.legacy(stack);
        return s != null && this.mergeItemStack(s, start, end, reverse);
    }

    public static int getRedstoneSignalFromContainer(IInventory inv) {
        return Container.calcRedstoneFromInventory(inv);
    }

    public static boolean stillValid(ContainerLevelAccess access, EntityPlayer player, Block block) {
        return access != null && access.world != null
            ? access.world.getBlock(access.pos.getX(), access.pos.getY(), access.pos.getZ()) == block
                && player.getDistanceSq(access.pos.getX() + 0.5, access.pos.getY() + 0.5, access.pos.getZ() + 0.5) <= 64.0
            : true;
    }

    public boolean canInteractWith(EntityPlayer player) {
        return this.stillValid(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return M.legacy(this.quickMoveStack(player, index));
    }

    public void onContainerClosed(EntityPlayer player) {
        this.removed(player);
    }
}
