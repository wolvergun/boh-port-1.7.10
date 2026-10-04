package net.mcreator.boh.compat.mc.world.level.saveddata;

import net.minecraft.nbt.NBTTagCompound;

public abstract class SavedData {
    DataHolder holder;

    public abstract NBTTagCompound save(NBTTagCompound var1);

    public void setDirty() {
        if (this.holder != null) {
            this.holder.markDirty();
        }
    }

    public void setDirty(boolean dirty) {
        if (dirty) {
            this.setDirty();
        }
    }

    public boolean isDirty() {
        return this.holder != null && this.holder.isDirty();
    }
}
