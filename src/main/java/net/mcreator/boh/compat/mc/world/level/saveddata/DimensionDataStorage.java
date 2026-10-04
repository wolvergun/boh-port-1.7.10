package net.mcreator.boh.compat.mc.world.level.saveddata;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;

public final class DimensionDataStorage {
    private final MapStorage storage;

    public DimensionDataStorage(MapStorage storage) {
        this.storage = storage;
    }

    public static DimensionDataStorage of(World w) {
        return new DimensionDataStorage(w.perWorldStorage);
    }

    public <T extends SavedData> T computeIfAbsent(Function<NBTTagCompound, T> load, Supplier<T> create, String name) {
        DataHolder.LOADERS.put(name, load);
        DataHolder h = (DataHolder)this.storage.loadData(DataHolder.class, name);
        if (h == null) {
            h = new DataHolder(name);
            this.storage.setData(name, h);
        }

        if (h.data == null) {
            h.data = create.get();
            h.data.holder = h;
            h.markDirty();
        }

        return (T)h.data;
    }

    public <T extends SavedData> T get(Function<NBTTagCompound, T> load, String name) {
        DataHolder.LOADERS.put(name, load);
        DataHolder h = (DataHolder)this.storage.loadData(DataHolder.class, name);
        return (T)(h == null ? null : h.data);
    }
}
