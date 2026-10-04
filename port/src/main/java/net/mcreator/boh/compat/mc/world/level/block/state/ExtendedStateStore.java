package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

/** Per-world storage for block state properties that do not fit into metadata. */
public class ExtendedStateStore extends WorldSavedData {

    private static final String NAME = "boh_extended_states";
    private final Map<Long, Integer> values = new HashMap<>();

    public ExtendedStateStore(String name) {
        super(name);
    }

    public static ExtendedStateStore get(World world) {
        ExtendedStateStore s = (ExtendedStateStore) world.perWorldStorage.loadData(ExtendedStateStore.class, NAME);
        if (s == null) {
            s = new ExtendedStateStore(NAME);
            world.perWorldStorage.setData(NAME, s);
        }
        return s;
    }

    private static long key(int x, int y, int z) {
        return ((long) x & 0x3FFFFFFL) << 38 | ((long) z & 0x3FFFFFFL) << 12 | ((long) y & 0xFFFL);
    }

    public int getExt(int x, int y, int z) {
        Integer v = values.get(key(x, y, z));
        return v == null ? 0 : v;
    }

    public void setExt(int x, int y, int z, int ext) {
        Integer old = ext == 0 ? values.remove(key(x, y, z)) : values.put(key(x, y, z), ext);
        if (old == null || old != ext) markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        values.clear();
        long[] keys = toLongs(tag.getIntArray("k_hi"), tag.getIntArray("k_lo"));
        int[] vals = tag.getIntArray("v");
        for (int i = 0; i < Math.min(keys.length, vals.length); i++) values.put(keys[i], vals[i]);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        int n = values.size(), i = 0;
        int[] hi = new int[n], lo = new int[n], v = new int[n];
        for (Map.Entry<Long, Integer> e : values.entrySet()) {
            hi[i] = (int) (e.getKey() >>> 32);
            lo[i] = (int) (long) e.getKey();
            v[i++] = e.getValue();
        }
        tag.setIntArray("k_hi", hi);
        tag.setIntArray("k_lo", lo);
        tag.setIntArray("v", v);
    }

    private static long[] toLongs(int[] hi, int[] lo) {
        long[] out = new long[Math.min(hi.length, lo.length)];
        for (int i = 0; i < out.length; i++) out[i] = ((long) hi[i] << 32) | (lo[i] & 0xFFFFFFFFL);
        return out;
    }
}
