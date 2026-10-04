package net.mcreator.boh.compat.mc.network.syncher;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;

/**
 * 1.20 SynchedEntityData on top of the entity's 1.7.10 {@link DataWatcher}. Accessor ids are handed out per
 * class from the DataWatcher slots vanilla living entities leave free.
 */
public class SynchedEntityData {

    /** Slots unused by EntityLivingBase / EntityLiving / EntityAgeable / EntityTameable / EntityHorse. */
    private static final int[] FREE_SLOTS = { 13, 14, 15, 23, 24, 25, 26, 27, 28, 29, 30, 31, 18, 21, 22 };
    private static final Map<Class<?>, Integer> COUNTS = new HashMap<>();

    private final Entity entity;
    private final DataWatcher watcher;

    public SynchedEntityData(Entity entity) {
        this.entity = entity;
        this.watcher = entity.getDataWatcher();
    }

    public static synchronized <T> EntityDataAccessor<T> defineId(Class<?> owner, EntityDataSerializer<T> serializer) {
        int base = 0;
        for (Class<?> c = owner.getSuperclass(); c != null; c = c.getSuperclass()) {
            Integer n = COUNTS.get(c);
            if (n != null) base += n;
        }
        int index = base + COUNTS.merge(owner, 1, Integer::sum) - 1;
        int slot = index < FREE_SLOTS.length ? FREE_SLOTS[index] : 32 + index;
        return new EntityDataAccessor<>(slot, serializer);
    }

    public <T> void define(EntityDataAccessor<T> accessor, T value) {
        Class<T> type = accessor.serializer.type;
        if (type == Boolean.class) watcher.addObject(accessor.id, Byte.valueOf((byte) (Boolean.TRUE.equals(value) ? 1 : 0)));
        else if (type == Integer.class) watcher.addObject(accessor.id, value == null ? 0 : (Integer) value);
        else if (type == Float.class) watcher.addObject(accessor.id, value == null ? 0f : (Float) value);
        else if (type == Byte.class) watcher.addObject(accessor.id, value == null ? (byte) 0 : (Byte) value);
        else watcher.addObject(accessor.id, value == null ? "" : value.toString());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(EntityDataAccessor<T> accessor) {
        Class<T> type = accessor.serializer.type;
        if (type == Boolean.class) return (T) Boolean.valueOf(watcher.getWatchableObjectByte(accessor.id) != 0);
        if (type == Integer.class) return (T) Integer.valueOf(watcher.getWatchableObjectInt(accessor.id));
        if (type == Float.class) return (T) Float.valueOf(watcher.getWatchableObjectFloat(accessor.id));
        if (type == Byte.class) return (T) Byte.valueOf(watcher.getWatchableObjectByte(accessor.id));
        return (T) watcher.getWatchableObjectString(accessor.id);
    }

    public <T> void set(EntityDataAccessor<T> accessor, T value) {
        Class<T> type = accessor.serializer.type;
        if (type == Boolean.class) watcher.updateObject(accessor.id, Byte.valueOf((byte) (Boolean.TRUE.equals(value) ? 1 : 0)));
        else if (type == Integer.class) watcher.updateObject(accessor.id, value == null ? 0 : (Integer) value);
        else if (type == Float.class) watcher.updateObject(accessor.id, value == null ? 0f : (Float) value);
        else if (type == Byte.class) watcher.updateObject(accessor.id, value == null ? (byte) 0 : (Byte) value);
        else watcher.updateObject(accessor.id, value == null ? "" : value.toString());
    }

    public <T> void set(EntityDataAccessor<T> accessor, T value, boolean force) {
        set(accessor, value);
    }

    public Entity getEntity() {
        return entity;
    }
}
