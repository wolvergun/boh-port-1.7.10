package net.mcreator.boh.compat.mc.network.syncher;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;

public class SynchedEntityData {
    private static final int[] FREE_SLOTS = new int[]{13, 14, 15, 23, 24, 25, 26, 27, 28, 29, 30, 31, 18, 21, 22};
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
            if (n != null) {
                base += n;
            }
        }

        int index = base + COUNTS.merge(owner, 1, Integer::sum) - 1;
        int slot = index < FREE_SLOTS.length ? FREE_SLOTS[index] : 32 + index;
        return new EntityDataAccessor<>(slot, serializer);
    }

    public <T> void define(EntityDataAccessor<T> accessor, T value) {
        Class<T> type = accessor.serializer.type;
        if (type == Boolean.class) {
            this.watcher.addObject(accessor.id, (byte)(Boolean.TRUE.equals(value) ? 1 : 0));
        } else if (type == Integer.class) {
            this.watcher.addObject(accessor.id, value == null ? 0 : (Integer)value);
        } else if (type == Float.class) {
            this.watcher.addObject(accessor.id, value == null ? 0.0F : (Float)value);
        } else if (type == Byte.class) {
            this.watcher.addObject(accessor.id, value == null ? 0 : (Byte)value);
        } else {
            this.watcher.addObject(accessor.id, value == null ? "" : value.toString());
        }
    }

    public <T> T get(EntityDataAccessor<T> accessor) {
        Class<T> type = accessor.serializer.type;
        if (type == Boolean.class) {
            return (T)this.watcher.getWatchableObjectByte(accessor.id) != 0;
        } else if (type == Integer.class) {
            return (T)this.watcher.getWatchableObjectInt(accessor.id);
        } else if (type == Float.class) {
            return (T)this.watcher.getWatchableObjectFloat(accessor.id);
        } else {
            return (T)(type == Byte.class ? this.watcher.getWatchableObjectByte(accessor.id) : this.watcher.getWatchableObjectString(accessor.id));
        }
    }

    public <T> void set(EntityDataAccessor<T> accessor, T value) {
        Class<T> type = accessor.serializer.type;
        if (type == Boolean.class) {
            this.watcher.updateObject(accessor.id, (byte)(Boolean.TRUE.equals(value) ? 1 : 0));
        } else if (type == Integer.class) {
            this.watcher.updateObject(accessor.id, value == null ? 0 : (Integer)value);
        } else if (type == Float.class) {
            this.watcher.updateObject(accessor.id, value == null ? 0.0F : (Float)value);
        } else if (type == Byte.class) {
            this.watcher.updateObject(accessor.id, value == null ? 0 : (Byte)value);
        } else {
            this.watcher.updateObject(accessor.id, value == null ? "" : value.toString());
        }
    }

    public <T> void set(EntityDataAccessor<T> accessor, T value, boolean force) {
        this.set(accessor, value);
    }

    public Entity getEntity() {
        return this.entity;
    }
}
