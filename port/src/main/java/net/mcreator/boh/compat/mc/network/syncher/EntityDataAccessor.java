package net.mcreator.boh.compat.mc.network.syncher;

/** A synced entity field: a DataWatcher slot plus its value type. */
public final class EntityDataAccessor<T> {

    final int id;
    final EntityDataSerializer<T> serializer;

    EntityDataAccessor(int id, EntityDataSerializer<T> serializer) {
        this.id = id;
        this.serializer = serializer;
    }

    public int getId() {
        return id;
    }

    public EntityDataSerializer<T> getSerializer() {
        return serializer;
    }

    @Override
    public String toString() {
        return "EntityDataAccessor{id=" + id + "}";
    }
}
