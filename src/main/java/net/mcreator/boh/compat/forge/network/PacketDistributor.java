package net.mcreator.boh.compat.forge.network;

import java.util.function.Supplier;
import net.minecraft.entity.player.EntityPlayerMP;

public final class PacketDistributor<T> {
    public static final PacketDistributor<EntityPlayerMP> PLAYER = new PacketDistributor<>("player");
    public static final PacketDistributor<Void> ALL = new PacketDistributor<>("all");
    public static final PacketDistributor<Object> DIMENSION = new PacketDistributor<>("dimension");
    public static final PacketDistributor<Void> SERVER = new PacketDistributor<>("server");
    final String kind;

    private PacketDistributor(String kind) {
        this.kind = kind;
    }

    public PacketDistributor.Target with(Supplier<T> s) {
        return new PacketDistributor.Target(this.kind, s.get());
    }

    public PacketDistributor.Target noArg() {
        return new PacketDistributor.Target(this.kind, null);
    }

    public static final class Target {
        public final String kind;
        public final Object arg;

        Target(String kind, Object arg) {
            this.kind = kind;
            this.arg = arg;
        }
    }
}
