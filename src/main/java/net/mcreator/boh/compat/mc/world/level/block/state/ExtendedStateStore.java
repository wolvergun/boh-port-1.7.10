package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class ExtendedStateStore extends WorldSavedData {
    private static final String NAME = "boh_extended_states";
    private final Map<Long, Integer> values = new HashMap<>();

    public ExtendedStateStore(String name) {
        super(name);
    }

    public static ExtendedStateStore get(World world) {
        ExtendedStateStore s = (ExtendedStateStore)world.perWorldStorage.loadData(ExtendedStateStore.class, "boh_extended_states");
        if (s == null) {
            s = new ExtendedStateStore("boh_extended_states");
            world.perWorldStorage.setData("boh_extended_states", s);
        }

        return s;
    }

    private static long key(int x, int y, int z) {
        return (x & 67108863L) << 38 | (z & 67108863L) << 12 | y & 4095L;
    }

    public int getExt(int x, int y, int z) {
        Integer v = this.values.get(key(x, y, z));
        return v == null ? 0 : v;
    }

    public void setExt(int x, int y, int z, int ext) {
        Integer old = ext == 0 ? this.values.remove(key(x, y, z)) : this.values.put(key(x, y, z), ext);
        if (old == null || old != ext) {
            this.markDirty();
        }
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.values.clear();
        long[] keys = toLongs(tag.getIntArray("k_hi"), tag.getIntArray("k_lo"));
        int[] vals = tag.getIntArray("v");

        for (int i = 0; i < Math.min(keys.length, vals.length); i++) {
            this.values.put(keys[i], vals[i]);
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        int n = this.values.size();
        int i = 0;
        int[] hi = new int[n];
        int[] lo = new int[n];
        int[] v = new int[n];

        for (Entry<Long, Integer> e : this.values.entrySet()) {
            hi[i] = (int)(e.getKey() >>> 32);
            lo[i] = (int)e.getKey().longValue();
            v[i++] = e.getValue();
        }

        tag.setIntArray("k_hi", hi);
        tag.setIntArray("k_lo", lo);
        tag.setIntArray("v", v);
    }

    private static long[] toLongs(int[] hi, int[] lo) {
        long[] out = new long[Math.min(hi.length, lo.length)];

        for (int i = 0; i < out.length; i++) {
            out[i] = (long)hi[i] << 32 | lo[i] & 4294967295L;
        }

        return out;
    }
}
