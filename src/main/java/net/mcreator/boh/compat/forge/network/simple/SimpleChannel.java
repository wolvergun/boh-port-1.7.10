package net.mcreator.boh.compat.forge.network.simple;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.mcreator.boh.compat.forge.network.Context;
import net.mcreator.boh.compat.forge.network.NetworkDirection;
import net.mcreator.boh.compat.forge.network.PacketDistributor;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SimpleChannel {
    private static final Map<String, SimpleChannel> CHANNELS = new HashMap<>();
    private final String name;
    private final SimpleNetworkWrapper wrapper;
    private final List<SimpleChannel.Codec<?>> codecs = new ArrayList<>();
    private final Map<Class<?>, Integer> index = new HashMap<>();

    public SimpleChannel(String name) {
        this.name = name;
        this.wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(name);
        CHANNELS.put(name, this);
        this.wrapper.registerMessage(SimpleChannel.ServerHandler.class, SimpleChannel.Envelope.class, 0, Side.SERVER);
        this.wrapper.registerMessage(SimpleChannel.ClientHandler.class, SimpleChannel.Envelope.class, 1, Side.CLIENT);
    }

    public <M> void registerMessage(
        int id, Class<M> type, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, Supplier<Context>> handler
    ) {
        this.index.put(type, this.codecs.size());
        this.codecs.add(new SimpleChannel.Codec<>(type, encoder, decoder, handler));
    }

    public <M> void registerMessage(
        int id,
        Class<M> type,
        BiConsumer<M, FriendlyByteBuf> encoder,
        Function<FriendlyByteBuf, M> decoder,
        BiConsumer<M, Supplier<Context>> handler,
        Optional<?> direction
    ) {
        this.registerMessage(id, type, encoder, decoder, handler);
    }

    private <M> SimpleChannel.Envelope wrap(M msg) {
        Integer i = this.index.get(msg.getClass());
        if (i == null) {
            for (Entry<Class<?>, Integer> e : this.index.entrySet()) {
                if (e.getKey().isInstance(msg)) {
                    i = e.getValue();
                }
            }
        }

        if (i == null) {
            throw new IllegalArgumentException("Unregistered message " + msg.getClass());
        } else {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            this.codecs.get(i).encoder.accept(msg, buf);
            SimpleChannel.Envelope env = new SimpleChannel.Envelope();
            env.channel = this.name;
            env.id = i;
            env.payload = new byte[buf.unwrap().readableBytes()];
            buf.unwrap().readBytes(env.payload);
            return env;
        }
    }

    public <M> void sendToServer(M msg) {
        this.wrapper.sendToServer(this.wrap(msg));
    }

    public <M> void send(PacketDistributor.Target target, M msg) {
        SimpleChannel.Envelope env = this.wrap(msg);
        String var4 = target.kind;
        switch (var4) {
            case "player":
                this.wrapper.sendTo(env, (EntityPlayerMP)target.arg);
                break;
            case "dimension":
                Object a = target.arg;
                int dim = a instanceof ResourceKey ? Dimensions.id((ResourceKey<?>)a) : (a instanceof Integer ? (Integer)a : 0);
                this.wrapper.sendToDimension(env, dim);
                break;
            case "server":
                this.wrapper.sendToServer(env);
                break;
            default:
                this.wrapper.sendToAll(env);
        }
    }

    public <M> void sendTo(M msg, Object connection, Object direction) {
        if (connection instanceof EntityPlayerMP) {
            this.wrapper.sendTo(this.wrap(msg), (EntityPlayerMP)connection);
        }
    }

    void dispatch(SimpleChannel.Envelope env, Context ctx) {
        SimpleChannel.Codec<Object> c = (SimpleChannel.Codec<Object>)this.codecs.get(env.id);
        Object msg = c.decoder.apply(new FriendlyByteBuf(Unpooled.wrappedBuffer(env.payload)));
        c.handler.accept(msg, () -> ctx);
    }

    public static final class ClientHandler implements IMessageHandler<SimpleChannel.Envelope, IMessage> {
        public IMessage onMessage(SimpleChannel.Envelope m, MessageContext ctx) {
            SimpleChannel c = SimpleChannel.CHANNELS.get(m.channel);
            if (c != null) {
                c.dispatch(m, new Context(null, NetworkDirection.PLAY_TO_CLIENT));
            }

            return null;
        }
    }

    static final class Codec<M> {
        final Class<M> type;
        final BiConsumer<M, FriendlyByteBuf> encoder;
        final Function<FriendlyByteBuf, M> decoder;
        final BiConsumer<M, Supplier<Context>> handler;

        Codec(Class<M> type, BiConsumer<M, FriendlyByteBuf> e, Function<FriendlyByteBuf, M> d, BiConsumer<M, Supplier<Context>> h) {
            this.type = type;
            this.encoder = e;
            this.decoder = d;
            this.handler = h;
        }
    }

    public static final class Envelope implements IMessage {
        String channel;
        int id;
        byte[] payload;

        public void fromBytes(ByteBuf b) {
            byte[] n = new byte[b.readUnsignedByte()];
            b.readBytes(n);
            this.channel = new String(n, StandardCharsets.UTF_8);
            this.id = b.readUnsignedShort();
            this.payload = new byte[b.readInt()];
            b.readBytes(this.payload);
        }

        public void toBytes(ByteBuf b) {
            byte[] n = this.channel.getBytes(StandardCharsets.UTF_8);
            b.writeByte(n.length);
            b.writeBytes(n);
            b.writeShort(this.id);
            b.writeInt(this.payload.length);
            b.writeBytes(this.payload);
        }
    }

    public static final class ServerHandler implements IMessageHandler<SimpleChannel.Envelope, IMessage> {
        public IMessage onMessage(SimpleChannel.Envelope m, MessageContext ctx) {
            SimpleChannel c = SimpleChannel.CHANNELS.get(m.channel);
            EntityPlayerMP p = ctx.getServerHandler().playerEntity;
            if (c != null) {
                c.dispatch(m, new Context(p, NetworkDirection.PLAY_TO_SERVER));
            }

            return null;
        }
    }
}
