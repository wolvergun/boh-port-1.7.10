package net.mcreator.boh.compat.mc.world;

import java.util.List;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** 1.20 Containers: drop container contents into the world. */
public final class Containers {

    private Containers() {}

    public static void dropContents(World w, BlockPos pos, IInventory inv) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            dropItemStack(w, pos.getX(), pos.getY(), pos.getZ(), inv.getStackInSlot(i));
            inv.setInventorySlotContents(i, null);
        }
    }

    public static void dropContents(World w, BlockPos pos, List<ItemStack> items) {
        for (ItemStack s : items) dropItemStack(w, pos.getX(), pos.getY(), pos.getZ(), s);
    }

    public static void dropItemStack(World w, double x, double y, double z, ItemStack stack) {
        ItemStack s = M.legacy(stack);
        if (s == null || w.isRemote) return;
        s = s.copy();
        while (s.stackSize > 0) {
            int n = Math.min(s.stackSize, 10 + w.rand.nextInt(21));
            ItemStack part = s.splitStack(n);
            EntityItem e = new EntityItem(w, x + 0.5 + w.rand.nextFloat() * 0.8 - 0.4, y + 0.5 + w.rand.nextFloat() * 0.8 - 0.4,
                z + 0.5 + w.rand.nextFloat() * 0.8 - 0.4, part);
            e.motionX = w.rand.nextGaussian() * 0.05;
            e.motionY = w.rand.nextGaussian() * 0.05 + 0.2;
            e.motionZ = w.rand.nextGaussian() * 0.05;
            w.spawnEntityInWorld(e);
        }
    }
}
