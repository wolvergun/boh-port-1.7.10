package net.mcreator.boh.compat.mc.world.level.saveddata;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public final class DataHolder extends WorldSavedData {
    static final Map<String, Function<NBTTagCompound, ? extends SavedData>> LOADERS = new HashMap<>();
    SavedData data;

    public DataHolder(String name) {
        super(name);
    }

    public void readFromNBT(NBTTagCompound tag) {
        Function<NBTTagCompound, ? extends SavedData> f = LOADERS.get(this.mapName);
        if (f != null) {
            this.data = f.apply(tag.getCompoundTag("data"));
            if (this.data != null) {
                this.data.holder = this;
            }
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        if (this.data != null) {
            tag.setTag("data", this.data.save(new NBTTagCompound()));
        }
    }
}
