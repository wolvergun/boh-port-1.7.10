package net.mcreator.boh.compat.net;

import io.netty.buffer.ByteBuf;
import net.mcreator.boh.compat.MClientImpl;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.registry.Registration;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/** Port-internal packets for things 1.7.10 cannot sync on its own (mod particles, action bar, cooldowns). */
public final class CompatNetwork {

    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("boh_compat");
    private static boolean registered;

    private CompatNetwork() {}

    public static void init() {
        if (registered) return;
        registered = true;
        CHANNEL.registerMessage(ParticleMsg.Handler.class, ParticleMsg.class, 0, Side.CLIENT);
        CHANNEL.registerMessage(TextMsg.Handler.class, TextMsg.class, 1, Side.CLIENT);
        CHANNEL.registerMessage(CooldownMsg.Handler.class, CooldownMsg.class, 2, Side.CLIENT);
        CHANNEL.registerMessage(BossBarMsg.Handler.class, BossBarMsg.class, 3, Side.CLIENT);
        CHANNEL.registerMessage(StopSoundMsg.Handler.class, StopSoundMsg.class, 4, Side.CLIENT);
    }

    public static void sendParticles(WorldServer w, ParticleType<?> type, double x, double y, double z, int count, double dx, double dy,
        double dz, double speed) {
        int idx = Registration.PARTICLES.indexOf(type);
        if (idx < 0) return;
        CHANNEL.sendToAllAround(new ParticleMsg(idx, x, y, z, count, dx, dy, dz, speed),
            new NetworkRegistry.TargetPoint(w.provider.dimensionId, x, y, z, 64));
    }

    /** Stops a sound (1.7.10 name, empty = all) on the client of the player. */
    /** Fake players (other mods' machines using items, the self-test) have no connection to send to. */
    public static boolean connected(EntityPlayerMP p) {
        return p != null && !(p instanceof net.minecraftforge.common.util.FakePlayer) && p.playerNetServerHandler != null;
    }

    private static void sendTo(cpw.mods.fml.common.network.simpleimpl.IMessage m, EntityPlayerMP p) {
        if (!connected(p)) return;
        CHANNEL.sendTo(m, p);
    }

    public static void sendStopSound(EntityPlayerMP p, String legacyName) {
        sendTo(new StopSoundMsg(legacyName), p);
    }

    public static final class StopSoundMsg implements IMessage {

        String name;

        public StopSoundMsg() {}

        StopSoundMsg(String name) {
            this.name = name;
        }

        @Override
        public void fromBytes(ByteBuf b) {
            name = ByteBufUtils.readUTF8String(b);
        }

        @Override
        public void toBytes(ByteBuf b) {
            ByteBufUtils.writeUTF8String(b, name);
        }

        public static final class Handler implements IMessageHandler<StopSoundMsg, IMessage> {

            @Override
            public IMessage onMessage(StopSoundMsg m, MessageContext ctx) {
                MClientImpl.stopSound(m.name);
                return null;
            }
        }
    }

    public static void sendActionBar(EntityPlayerMP p, String text) {
        sendTo(new TextMsg(text), p);
    }

    public static void sendCooldown(EntityPlayerMP p, Item item, int ticks) {
        sendTo(new CooldownMsg(Item.getIdFromItem(item), ticks), p);
    }

    public static void sendBossBar(EntityPlayerMP p, java.util.UUID id, String name, float progress, int color, int notches, boolean show) {
        sendTo(new BossBarMsg(id, name == null ? "" : name, progress, color, notches, show), p);
    }

    public static final class BossBarMsg implements IMessage {

        java.util.UUID id;
        String name;
        float progress;
        int color, notches;
        boolean show;

        public BossBarMsg() {}

        BossBarMsg(java.util.UUID id, String name, float progress, int color, int notches, boolean show) {
            this.id = id;
            this.name = name;
            this.progress = progress;
            this.color = color;
            this.notches = notches;
            this.show = show;
        }

        @Override
        public void fromBytes(ByteBuf b) {
            id = new java.util.UUID(b.readLong(), b.readLong());
            name = ByteBufUtils.readUTF8String(b);
            progress = b.readFloat();
            color = b.readInt();
            notches = b.readInt();
            show = b.readBoolean();
        }

        @Override
        public void toBytes(ByteBuf b) {
            b.writeLong(id.getMostSignificantBits());
            b.writeLong(id.getLeastSignificantBits());
            ByteBufUtils.writeUTF8String(b, name);
            b.writeFloat(progress);
            b.writeInt(color);
            b.writeInt(notches);
            b.writeBoolean(show);
        }

        public static final class Handler implements IMessageHandler<BossBarMsg, IMessage> {

            @Override
            public IMessage onMessage(BossBarMsg m, MessageContext ctx) {
                MClientImpl.bossBar(m.id, m.name, m.progress, m.color, m.notches, m.show);
                return null;
            }
        }
    }

    public static final class ParticleMsg implements IMessage {

        int type, count;
        double x, y, z, dx, dy, dz, speed;

        public ParticleMsg() {}

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

        @Override
        public void fromBytes(ByteBuf b) {
            type = b.readInt();
            count = b.readInt();
            x = b.readDouble();
            y = b.readDouble();
            z = b.readDouble();
            dx = b.readDouble();
            dy = b.readDouble();
            dz = b.readDouble();
            speed = b.readDouble();
        }

        @Override
        public void toBytes(ByteBuf b) {
            b.writeInt(type);
            b.writeInt(count);
            b.writeDouble(x);
            b.writeDouble(y);
            b.writeDouble(z);
            b.writeDouble(dx);
            b.writeDouble(dy);
            b.writeDouble(dz);
            b.writeDouble(speed);
        }

        public static final class Handler implements IMessageHandler<ParticleMsg, IMessage> {

            @Override
            public IMessage onMessage(ParticleMsg m, MessageContext ctx) {
                if (m.type < 0 || m.type >= Registration.PARTICLES.size()) return null;
                ParticleType<?> t = Registration.PARTICLES.get(m.type);
                MClientImpl.spawnParticleBurst(t, m.x, m.y, m.z, m.count, m.dx, m.dy, m.dz, m.speed);
                return null;
            }
        }
    }

    public static final class TextMsg implements IMessage {

        String text;

        public TextMsg() {}

        TextMsg(String text) {
            this.text = text;
        }

        @Override
        public void fromBytes(ByteBuf b) {
            text = ByteBufUtils.readUTF8String(b);
        }

        @Override
        public void toBytes(ByteBuf b) {
            ByteBufUtils.writeUTF8String(b, text);
        }

        public static final class Handler implements IMessageHandler<TextMsg, IMessage> {

            @Override
            public IMessage onMessage(TextMsg m, MessageContext ctx) {
                MClientImpl.setActionBar(m.text);
                return null;
            }
        }
    }

    public static final class CooldownMsg implements IMessage {

        int item, ticks;

        public CooldownMsg() {}

        CooldownMsg(int item, int ticks) {
            this.item = item;
            this.ticks = ticks;
        }

        @Override
        public void fromBytes(ByteBuf b) {
            item = b.readInt();
            ticks = b.readInt();
        }

        @Override
        public void toBytes(ByteBuf b) {
            b.writeInt(item);
            b.writeInt(ticks);
        }

        public static final class Handler implements IMessageHandler<CooldownMsg, IMessage> {

            @Override
            public IMessage onMessage(CooldownMsg m, MessageContext ctx) {
                MClientImpl.applyCooldown(Item.getItemById(m.item), m.ticks);
                return null;
            }
        }
    }
}
