package net.mcreator.boh.compat.net;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.mcreator.boh.compat.MClientImpl;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.registry.Registration;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.world.WorldServer;

public final class CompatNetwork {
    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("boh_compat");
    private static boolean registered;

    private CompatNetwork() {
    }

    public static void init() {
        if (!registered) {
            registered = true;
            CHANNEL.registerMessage(CompatNetwork.ParticleMsg.Handler.class, CompatNetwork.ParticleMsg.class, 0, Side.CLIENT);
            CHANNEL.registerMessage(CompatNetwork.TextMsg.Handler.class, CompatNetwork.TextMsg.class, 1, Side.CLIENT);
            CHANNEL.registerMessage(CompatNetwork.CooldownMsg.Handler.class, CompatNetwork.CooldownMsg.class, 2, Side.CLIENT);
            CHANNEL.registerMessage(CompatNetwork.BossBarMsg.Handler.class, CompatNetwork.BossBarMsg.class, 3, Side.CLIENT);
            CHANNEL.registerMessage(CompatNetwork.StopSoundMsg.Handler.class, CompatNetwork.StopSoundMsg.class, 4, Side.CLIENT);
        }
    }

    public static void sendParticles(
        WorldServer w, ParticleType<?> type, double x, double y, double z, int count, double dx, double dy, double dz, double speed
    ) {
        int idx = Registration.PARTICLES.indexOf(type);
        if (idx >= 0) {
            CHANNEL.sendToAllAround(
                new CompatNetwork.ParticleMsg(idx, x, y, z, count, dx, dy, dz, speed), new TargetPoint(w.provider.dimensionId, x, y, z, 64.0)
            );
        }
    }

    public static void sendStopSound(EntityPlayerMP p, String legacyName) {
        CHANNEL.sendTo(new CompatNetwork.StopSoundMsg(legacyName), p);
    }

    public static void sendActionBar(EntityPlayerMP p, String text) {
        CHANNEL.sendTo(new CompatNetwork.TextMsg(text), p);
    }

    public static void sendCooldown(EntityPlayerMP p, Item item, int ticks) {
        CHANNEL.sendTo(new CompatNetwork.CooldownMsg(Item.getIdFromItem(item), ticks), p);
    }

    public static void sendBossBar(EntityPlayerMP p, UUID id, String name, float progress, int color, int notches, boolean show) {
        CHANNEL.sendTo(new CompatNetwork.BossBarMsg(id, name == null ? "" : name, progress, color, notches, show), p);
    }

    public static final class BossBarMsg implements IMessage {
        UUID id;
        String name;
        float progress;
        int color;
        int notches;
        boolean show;

        public BossBarMsg() {
        }

        BossBarMsg(UUID id, String name, float progress, int color, int notches, boolean show) {
            this.id = id;
            this.name = name;
            this.progress = progress;
            this.color = color;
            this.notches = notches;
            this.show = show;
        }

        public void fromBytes(ByteBuf b) {
            this.id = new UUID(b.readLong(), b.readLong());
            this.name = ByteBufUtils.readUTF8String(b);
            this.progress = b.readFloat();
            this.color = b.readInt();
            this.notches = b.readInt();
            this.show = b.readBoolean();
        }

        public void toBytes(ByteBuf b) {
            b.writeLong(this.id.getMostSignificantBits());
            b.writeLong(this.id.getLeastSignificantBits());
            ByteBufUtils.writeUTF8String(b, this.name);
            b.writeFloat(this.progress);
            b.writeInt(this.color);
            b.writeInt(this.notches);
            b.writeBoolean(this.show);
        }

        public static final class Handler implements IMessageHandler<CompatNetwork.BossBarMsg, IMessage> {
            public IMessage onMessage(CompatNetwork.BossBarMsg m, MessageContext ctx) {
                MClientImpl.bossBar(m.id, m.name, m.progress, m.color, m.notches, m.show);
                return null;
            }
        }
    }

    public static final class CooldownMsg implements IMessage {
        int item;
        int ticks;

        public CooldownMsg() {
        }

        CooldownMsg(int item, int ticks) {
            this.item = item;
            this.ticks = ticks;
        }

        public void fromBytes(ByteBuf b) {
            this.item = b.readInt();
            this.ticks = b.readInt();
        }

        public void toBytes(ByteBuf b) {
            b.writeInt(this.item);
            b.writeInt(this.ticks);
        }

        public static final class Handler implements IMessageHandler<CompatNetwork.CooldownMsg, IMessage> {
            public IMessage onMessage(CompatNetwork.CooldownMsg m, MessageContext ctx) {
                MClientImpl.applyCooldown(Item.getItemById(m.item), m.ticks);
                return null;
            }
        }
    }

    public static final class ParticleMsg implements IMessage {
        int type;
        int count;
        double x;
        double y;
        double z;
        double dx;
        double dy;
        double dz;
        double speed;

        public ParticleMsg() {
        }

        ParticleMsg(int type, double x, double y, double z, int count, double dx, double dy, double dz, double speed) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
            this.count = count;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            this.speed = speed;
        }

        public void fromBytes(ByteBuf b) {
            this.type = b.readInt();
            this.count = b.readInt();
            this.x = b.readDouble();
            this.y = b.readDouble();
            this.z = b.readDouble();
            this.dx = b.readDouble();
            this.dy = b.readDouble();
            this.dz = b.readDouble();
            this.speed = b.readDouble();
        }

        public void toBytes(ByteBuf b) {
            b.writeInt(this.type);
            b.writeInt(this.count);
            b.writeDouble(this.x);
            b.writeDouble(this.y);
            b.writeDouble(this.z);
            b.writeDouble(this.dx);
            b.writeDouble(this.dy);
            b.writeDouble(this.dz);
            b.writeDouble(this.speed);
        }

        public static final class Handler implements IMessageHandler<CompatNetwork.ParticleMsg, IMessage> {
            public IMessage onMessage(CompatNetwork.ParticleMsg m, MessageContext ctx) {
                if (m.type >= 0 && m.type < Registration.PARTICLES.size()) {
                    ParticleType<?> t = Registration.PARTICLES.get(m.type);
                    MClientImpl.spawnParticleBurst(t, m.x, m.y, m.z, m.count, m.dx, m.dy, m.dz, m.speed);
                    return null;
                } else {
                    return null;
                }
            }
        }
    }

    public static final class StopSoundMsg implements IMessage {
        String name;

        public StopSoundMsg() {
        }

        StopSoundMsg(String name) {
            this.name = name;
        }

        public void fromBytes(ByteBuf b) {
            this.name = ByteBufUtils.readUTF8String(b);
        }

        public void toBytes(ByteBuf b) {
            ByteBufUtils.writeUTF8String(b, this.name);
        }

        public static final class Handler implements IMessageHandler<CompatNetwork.StopSoundMsg, IMessage> {
            public IMessage onMessage(CompatNetwork.StopSoundMsg m, MessageContext ctx) {
                MClientImpl.stopSound(m.name);
                return null;
            }
        }
    }

    public static final class TextMsg implements IMessage {
        String text;

        public TextMsg() {
        }

        TextMsg(String text) {
            this.text = text;
        }

        public void fromBytes(ByteBuf b) {
            this.text = ByteBufUtils.readUTF8String(b);
        }

        public void toBytes(ByteBuf b) {
            ByteBufUtils.writeUTF8String(b, this.text);
        }

        public static final class Handler implements IMessageHandler<CompatNetwork.TextMsg, IMessage> {
            public IMessage onMessage(CompatNetwork.TextMsg m, MessageContext ctx) {
                MClientImpl.setActionBar(m.text);
                return null;
            }
        }
    }
}
