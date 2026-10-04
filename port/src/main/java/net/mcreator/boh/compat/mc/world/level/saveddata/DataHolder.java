package net.mcreator.boh.compat.mc.world.level.saveddata;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

/** 1.7.10 WorldSavedData wrapper around a 1.20 SavedData. */
public final class DataHolder extends WorldSavedData {

    static final Map<String, Function<NBTTagCompound, ? extends SavedData>> LOADERS = new HashMap<>();

    SavedData data;

    public DataHolder(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        Function<NBTTagCompound, ? extends SavedData> f = LOADERS.get(mapName);
        if (f != null) {
            data = f.apply(tag.getCompoundTag("data"));
            if (data != null) data.holder = this;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        if (data != null) tag.setTag("data", data.save(new NBTTagCompound()));
    }
}
