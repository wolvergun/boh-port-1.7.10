package net.mcreator.boh.compat.mc.network.syncher;

public final class EntityDataSerializer<T> {
    final Class<T> type;

    EntityDataSerializer(Class<T> type) {
        this.type = type;
    }

    public Class<T> type() {
        return this.type;
    }
}
