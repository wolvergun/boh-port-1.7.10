package net.mcreator.boh.compat.mc.world;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public interface WorldlyContainer extends ISidedInventory {
    int[] getSlotsForFace(Direction var1);

    boolean canPlaceItemThroughFace(int var1, ItemStack var2, Direction var3);

    boolean canTakeItemThroughFace(int var1, ItemStack var2, Direction var3);

    default int[] getAccessibleSlotsFromSide(int side) {
        return this.getSlotsForFace(Direction.from3DDataValue(side));
    }

    default boolean canInsertItem(int slot, ItemStack stack, int side) {
        return this.canPlaceItemThroughFace(slot, M.stack(stack), Direction.from3DDataValue(side));
    }

    default boolean canExtractItem(int slot, ItemStack stack, int side) {
        return this.canTakeItemThroughFace(slot, M.stack(stack), Direction.from3DDataValue(side));
    }
}
