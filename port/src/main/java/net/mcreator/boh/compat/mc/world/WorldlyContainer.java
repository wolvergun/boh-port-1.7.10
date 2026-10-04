package net.mcreator.boh.compat.mc.world;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

/** 1.20 WorldlyContainer as a 1.7.10 ISidedInventory. */
public interface WorldlyContainer extends ISidedInventory {

    int[] getSlotsForFace(Direction side);

    boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction);

    boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction);

    @Override
    default int[] getAccessibleSlotsFromSide(int side) {
        return getSlotsForFace(Direction.from3DDataValue(side));
    }

    @Override
    default boolean canInsertItem(int slot, ItemStack stack, int side) {
        return canPlaceItemThroughFace(slot, M.stack(stack), Direction.from3DDataValue(side));
    }

    @Override
    default boolean canExtractItem(int slot, ItemStack stack, int side) {
        return canTakeItemThroughFace(slot, M.stack(stack), Direction.from3DDataValue(side));
    }
}
