package net.mcreator.boh.compat.mc.network.syncher;

/** Value type of a synced field; maps to a 1.7.10 DataWatcher object type. */
public final class EntityDataSerializer<T> {

    final Class<T> type;

    EntityDataSerializer(Class<T> type) {
        this.type = type;
    }

    public Class<T> type() {
        return type;
    }
}
