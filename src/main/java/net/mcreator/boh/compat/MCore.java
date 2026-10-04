package net.mcreator.boh.compat;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.IForgeRegistry;
import net.mcreator.boh.compat.forge.registries.ITagManager;
import net.mcreator.boh.compat.forge.registries.RegistryObject;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.Holder;
import net.mcreator.boh.compat.mc.core.Vec3i;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.StateDefinition;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

public class MCore extends MGen {
    protected MCore() {
    }

    public static NBTTagCompound new_NBTTagCompound() {
        return new NBTTagCompound();
    }

    public static NBTTagCompound new_CompoundTag() {
        return new NBTTagCompound();
    }

    public static boolean contains(NBTTagCompound tag, String key) {
        return tag != null && tag.hasKey(key);
    }

    public static boolean contains(NBTTagCompound tag, String key, int type) {
        return tag != null && tag.hasKey(key, type);
    }

    public static String getString(NBTTagCompound tag, String key) {
        return tag == null ? "" : tag.getString(key);
    }

    public static double getDouble(NBTTagCompound tag, String key) {
        return tag == null ? 0.0 : tag.getDouble(key);
    }

    public static float getFloat(NBTTagCompound tag, String key) {
        return tag == null ? 0.0F : tag.getFloat(key);
    }

    public static boolean getBoolean(NBTTagCompound tag, String key) {
        return tag != null && tag.getBoolean(key);
    }

    public static int getInt(NBTTagCompound tag, String key) {
        return tag == null ? 0 : tag.getInteger(key);
    }

    public static long getLong(NBTTagCompound tag, String key) {
        return tag == null ? 0L : tag.getLong(key);
    }

    public static byte getByte(NBTTagCompound tag, String key) {
        return tag == null ? 0 : tag.getByte(key);
    }

    public static NBTTagCompound getCompound(NBTTagCompound tag, String key) {
        return tag == null ? new NBTTagCompound() : tag.getCompoundTag(key);
    }

    public static NBTTagList getList(NBTTagCompound tag, String key, int type) {
        return tag == null ? new NBTTagList() : tag.getTagList(key, type);
    }

    public static NBTBase get(NBTTagCompound tag, String key) {
        return tag == null ? null : tag.getTag(key);
    }

    public static void putString(NBTTagCompound tag, String key, String v) {
        tag.setString(key, v == null ? "" : v);
    }

    public static void putDouble(NBTTagCompound tag, String key, double v) {
        tag.setDouble(key, v);
    }

    public static void putFloat(NBTTagCompound tag, String key, float v) {
        tag.setFloat(key, v);
    }

    public static void putBoolean(NBTTagCompound tag, String key, boolean v) {
        tag.setBoolean(key, v);
    }

    public static void putInt(NBTTagCompound tag, String key, int v) {
        tag.setInteger(key, v);
    }

    public static void putLong(NBTTagCompound tag, String key, long v) {
        tag.setLong(key, v);
    }

    public static void putByte(NBTTagCompound tag, String key, byte v) {
        tag.setByte(key, v);
    }

    public static NBTBase put(NBTTagCompound tag, String key, NBTBase v) {
        tag.setTag(key, v);
        return v;
    }

    public static void remove(NBTTagCompound tag, String key) {
        tag.removeTag(key);
    }

    public static Set<String> getAllKeys(NBTTagCompound tag) {
        return tag.func_150296_c();
    }

    public static NBTTagCompound copy(NBTTagCompound tag) {
        return (NBTTagCompound)tag.copy();
    }

    public static boolean isEmpty(NBTTagCompound tag) {
        return tag == null || tag.hasNoTags();
    }

    public static NBTTagCompound merge(NBTTagCompound into, NBTTagCompound from) {
        for (String k : getAllKeys(from)) {
            into.setTag(k, from.getTag(k).copy());
        }

        return into;
    }

    public static boolean add(NBTTagList list, NBTBase v) {
        list.appendTag(v);
        return true;
    }

    public static int size(NBTTagList list) {
        return list.tagCount();
    }

    public static NBTTagCompound getCompound(NBTTagList list, int i) {
        return list.getCompoundTagAt(i);
    }

    public static boolean contains(Collection<?> c, Object o) {
        return c != null && c.contains(o);
    }

    public static boolean contains(String s, CharSequence part) {
        return s != null && s.contains(part);
    }

    public static boolean contains(Map<?, ?> m, Object key) {
        return m != null && m.containsKey(key);
    }

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static boolean isEmpty(Optional<?> o) {
        return o == null || !o.isPresent();
    }

    public static boolean remove(Collection<?> c, Object o) {
        return c.remove(o);
    }

    public static <T> T remove(List<T> l, int i) {
        return l.remove(i);
    }

    public static <K, V> V remove(Map<K, V> m, Object k) {
        return m.remove(k);
    }

    public static <T> void set(AtomicReference<T> ref, T v) {
        ref.set(v);
    }

    public static <T> T set(List<T> l, int i, T v) {
        return l.set(i, v);
    }

    public static String getName(Enum<?> e) {
        return e.name().toLowerCase();
    }

    public static String getName(Class<?> c) {
        return c.getName();
    }

    public static void error(Logger log, String msg) {
        log.error(msg);
    }

    public static void error(Logger log, String msg, Object... args) {
        log.error(msg, args);
    }

    public static void info(Logger log, String msg, Object... args) {
        log.info(msg, args);
    }

    public static float nextFloat(Random r) {
        return r.nextFloat();
    }

    public static double nextDouble(Random r) {
        return r.nextDouble();
    }

    public static int nextInt(Random r, int bound) {
        return r.nextInt(bound);
    }

    public static int nextInt(Random r, int origin, int bound) {
        return RandomSource.wrap(r).nextInt(origin, bound);
    }

    public static boolean nextBoolean(Random r) {
        return r.nextBoolean();
    }

    public static double nextGaussian(Random r) {
        return r.nextGaussian();
    }

    public static <T, I extends T> RegistryObject<I> register(DeferredRegister<T> reg, String name, Supplier<? extends I> sup) {
        return reg.register(name, sup);
    }

    public static ResourceLocation getId(RegistryObject<?> o) {
        return o.getId();
    }

    public static <T> ITagManager<T> tags(IForgeRegistry<T> reg) {
        return reg.tags();
    }

    public static <T> ITagManager.ITag<T> getTag(ITagManager<T> mgr, TagKey<T> key) {
        return mgr.getTag(key);
    }

    public static <T> ITagManager.ITag<T> getTag(ITagManager<T> mgr, TagKey key, int unused) {
        return mgr.getTag(key);
    }

    public static <T> Optional<T> getRandomElement(ITagManager.ITag<T> tag, Random r) {
        return tag.getRandomElement(r);
    }

    public static <T> Optional<Holder<T>> getHolder(IForgeRegistry<T> reg, T value) {
        return reg.getHolder(value);
    }

    public static <T> T value(Optional<T> o) {
        return o.orElse(null);
    }

    public static <T> T value(Holder<T> h) {
        return h == null ? null : h.value();
    }

    public static ResourceLocation location(ResourceKey<?> key) {
        return key.location();
    }

    public static String getPath(ResourceLocation rl) {
        return rl.getResourcePath();
    }

    public static String getNamespace(ResourceLocation rl) {
        return rl.getResourceDomain();
    }

    public static String getString(Component c) {
        return c == null ? "" : c.getString();
    }

    public static String getString(String s) {
        return s;
    }

    public static Component copy(Component c) {
        return c.copy();
    }

    public static <T> void set(SynchedEntityData data, EntityDataAccessor<T> acc, T value) {
        data.set(acc, value);
    }

    public static <T> void set(SynchedEntityData data, EntityDataAccessor<T> acc, T value, boolean force) {
        data.set(acc, value);
    }

    public static BlockState any(StateDefinition def) {
        return def.any();
    }

    public static BlockState setValue(BlockState s, Property p, Object v) {
        return s.setValue(p, (Comparable)v);
    }

    public static <T extends Comparable<T>, V extends T> BlockState setValue(BlockState s, Property<T> p, V v) {
        return s.setValue(p, v);
    }

    public static Collection<Property<?>> getProperties(StateDefinition def) {
        return def.getProperties();
    }

    public static Property<?> getProperty(StateDefinition def, String name) {
        return def.getProperty(name);
    }

    public static List<BlockState> getPossibleStates(StateDefinition def) {
        return def.getPossibleStates();
    }

    public static <T extends Comparable<T>> Collection<T> getPossibleValues(Property<T> p) {
        return p.getPossibleValues();
    }

    public static String getName(Property<?> p) {
        return p.getName();
    }

    public static ImmutableMap<Property<?>, Comparable<?>> getValues(BlockState s) {
        return s.getValues();
    }

    public static int getX(BlockPos p) {
        return p.getX();
    }

    public static int getY(BlockPos p) {
        return p.getY();
    }

    public static int getZ(BlockPos p) {
        return p.getZ();
    }

    public static int getX(Vec3i p) {
        return p.getX();
    }

    public static int getY(Vec3i p) {
        return p.getY();
    }

    public static int getZ(Vec3i p) {
        return p.getZ();
    }

    public static double distanceToSqr(Vec3 a, Vec3 b) {
        return a.distanceToSqr(b);
    }

    public static double distanceToSqr(Vec3 a, double x, double y, double z) {
        return a.distanceToSqr(x, y, z);
    }

    public static Vec3 multiply(Vec3 v, double x, double y, double z) {
        return v.multiply(x, y, z);
    }

    public static Vec3 multiply(Vec3 v, Vec3 o) {
        return v.multiply(o);
    }

    public static Vec3 add(Vec3 v, double x, double y, double z) {
        return v.add(x, y, z);
    }

    public static Vec3 add(Vec3 v, Vec3 o) {
        return v.add(o);
    }

    public static double length(Vec3 v) {
        return v.length();
    }

    public static AABB move(AABB b, double x, double y, double z) {
        return b.move(x, y, z);
    }

    public static boolean contains(AABB b, Vec3 v) {
        return b.contains(v);
    }

    public static boolean contains(AABB b, double x, double y, double z) {
        return b.contains(x, y, z);
    }

    public static Direction getOpposite(Direction d) {
        return d.getOpposite();
    }

    public static String getName(Direction d) {
        return d.getName();
    }

    public static Optional<Vec3> clip(AABB b, Vec3 from, Vec3 to) {
        return b.clip(from, to);
    }
}
