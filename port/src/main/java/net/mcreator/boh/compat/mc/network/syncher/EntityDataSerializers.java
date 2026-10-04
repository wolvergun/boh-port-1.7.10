package net.mcreator.boh.compat.mc.network.syncher;

public final class EntityDataSerializers {

    public static final EntityDataSerializer<Boolean> BOOLEAN = new EntityDataSerializer<>(Boolean.class);
    public static final EntityDataSerializer<Integer> INT = new EntityDataSerializer<>(Integer.class);
    public static final EntityDataSerializer<Float> FLOAT = new EntityDataSerializer<>(Float.class);
    public static final EntityDataSerializer<String> STRING = new EntityDataSerializer<>(String.class);
    public static final EntityDataSerializer<Byte> BYTE = new EntityDataSerializer<>(Byte.class);

    private EntityDataSerializers() {}
}
