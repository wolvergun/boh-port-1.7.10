package net.mcreator.boh.compat.mc.network.syncher;

public final class EntityDataAccessor<T> {
    final int id;
    final EntityDataSerializer<T> serializer;

    EntityDataAccessor(int id, EntityDataSerializer<T> serializer) {
        this.id = id;
        this.serializer = serializer;
    }

    public int getId() {
        return this.id;
    }

    public EntityDataSerializer<T> getSerializer() {
        return this.serializer;
    }

    @Override
    public String toString() {
        return "EntityDataAccessor{id=" + this.id + "}";
    }
}
