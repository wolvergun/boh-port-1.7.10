package net.mcreator.boh.compat.mc.world;

import java.util.List;

import net.mcreator.boh.compat.M;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/** 1.20 ContainerHelper: "Items" list with byte "Slot". */
public final class ContainerHelper {

    private ContainerHelper() {}

    public static NBTTagCompound saveAllItems(NBTTagCompound tag, List<ItemStack> items) {
        return saveAllItems(tag, items, true);
    }

    public static NBTTagCompound saveAllItems(NBTTagCompound tag, List<ItemStack> items, boolean saveEmpty) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < items.size(); i++) {
            ItemStack s = M.legacy(items.get(i));
            if (s == null) continue;
            NBTTagCompound t = new NBTTagCompound();
            t.setByte("Slot", (byte) i);
            s.writeToNBT(t);
            list.appendTag(t);
        }
        if (list.tagCount() > 0 || saveEmpty) tag.setTag("Items", list);
        return tag;
    }

    public static void loadAllItems(NBTTagCompound tag, List<ItemStack> items) {
        NBTTagList list = tag.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound t = list.getCompoundTagAt(i);
            int slot = t.getByte("Slot") & 255;
            if (slot < items.size()) items.set(slot, M.stack(ItemStack.loadItemStackFromNBT(t)));
        }
    }

    public static ItemStack removeItem(List<ItemStack> items, int slot, int amount) {
        ItemStack s = M.legacy(items.get(slot));
        if (s == null || amount <= 0) return M.EMPTY;
        ItemStack out = s.splitStack(amount);
        if (s.stackSize <= 0) items.set(slot, M.EMPTY);
        return out;
    }

    public static ItemStack takeItem(List<ItemStack> items, int slot) {
        ItemStack s = items.get(slot);
        items.set(slot, M.EMPTY);
        return s;
    }
}
