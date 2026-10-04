package net.mcreator.boh.compat.mc.world.level.saveddata;

import net.minecraft.nbt.NBTTagCompound;

/** 1.20 SavedData; stored inside a 1.7.10 WorldSavedData holder (see DimensionDataStorage). */
public abstract class SavedData {

    DataHolder holder;

    public abstract NBTTagCompound save(NBTTagCompound tag);

    public void setDirty() {
        if (holder != null) holder.markDirty();
    }

    public void setDirty(boolean dirty) {
        if (dirty) setDirty();
    }

    public boolean isDirty() {
        return holder != null && holder.isDirty();
    }
}
