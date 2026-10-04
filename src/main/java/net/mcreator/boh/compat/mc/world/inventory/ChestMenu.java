package net.mcreator.boh.compat.mc.world.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;

public class ChestMenu extends AbstractContainerMenu {
    public final IInventory container;

    public ChestMenu(int id, IInventory container) {
        super(null, id);
        this.container = container;
    }

    public static ChestMenu threeRows(int id, InventoryPlayer inv) {
        return new ChestMenu(id, new InventoryBasic("container.chest", false, 27));
    }

    public static ChestMenu threeRows(int id, InventoryPlayer inv, IInventory container) {
        return new ChestMenu(id, container);
    }

    public static ChestMenu sixRows(int id, InventoryPlayer inv) {
        return new ChestMenu(id, new InventoryBasic("container.chestDouble", false, 54));
    }

    @Override
    public boolean stillValid(EntityPlayer player) {
        return true;
    }
}
