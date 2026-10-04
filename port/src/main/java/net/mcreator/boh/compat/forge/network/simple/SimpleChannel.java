package net.mcreator.boh.compat.forge.network.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.mcreator.boh.compat.forge.network.Context;
import net.mcreator.boh.compat.forge.network.NetworkDirection;
import net.mcreator.boh.compat.forge.network.PacketDistributor;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/** Forge SimpleChannel over one FML SimpleNetworkWrapper carrying (message index, payload) envelopes. */
public final class SimpleChannel {

    static final class Codec<M> {

        final Class<M> type;
        final BiConsumer<M, FriendlyByteBuf> encoder;
        final Function<FriendlyByteBuf, M> decoder;
        final BiConsumer<M, Supplier<Context>> handler;

        Codec(Class<M> type, BiConsumer<M, FriendlyByteBuf> e, Function<FriendlyByteBuf, M> d, BiConsumer<M, Supplier<Context>> h) {
            this.type = type;
            encoder = e;
            decoder = d;
            handler = h;
        }
    }

    private static final Map<String, SimpleChannel> CHANNELS = new HashMap<>();

    private final String name;
    private final SimpleNetworkWrapper wrapper;
    private final List<Codec<?>> codecs = new ArrayList<>();
    private final Map<Class<?>, Integer> index = new HashMap<>();

    public SimpleChannel(String name) {
        this.name = name;
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(name);
        CHANNELS.put(name, this);
        wrapper.registerMessage(ServerHandler.class, Envelope.class, 0, Side.SERVER);
        wrapper.registerMessage(ClientHandler.class, Envelope.class, 1, Side.CLIENT);
    }

    public <M> void registerMessage(int id, Class<M> type, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder,
        BiConsumer<M, Supplier<Context>> handler) {
        index.put(type, codecs.size());
        codecs.add(new Codec<>(type, encoder, decoder, handler));
    }

    public <M> void registerMessage(int id, Class<M> type, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder,
        BiConsumer<M, Supplier<Context>> handler, java.util.Optional<?> direction) {
        registerMessage(id, type, encoder, decoder, handler);
    }

    @SuppressWarnings("unchecked")
    private <M> Envelope wrap(M msg) {
        Integer i = index.get(msg.getClass());
        if (i == null) for (Map.Entry<Class<?>, Integer> e : index.entrySet()) if (e.getKey().isInstance(msg)) i = e.getValue();
        if (i == null) throw new IllegalArgumentException("Unregistered message " + msg.getClass());
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        ((Codec<M>) codecs.get(i)).encoder.accept(msg, buf);
        Envelope env = new Envelope();
        env.channel = name;
        env.id = i;
        env.payload = new byte[buf.unwrap().readableBytes()];
        buf.unwrap().readBytes(env.payload);
        return env;
    }

    public <M> void sendToServer(M msg) {
        wrapper.sendToServer(wrap(msg));
    }

    public <M> void send(PacketDistributor.Target target, M msg) {
        Envelope env = wrap(msg);
        switch (target.kind) {
            case "player":
                if (net.mcreator.boh.compat.net.CompatNetwork.connected((EntityPlayerMP) target.arg)) wrapper.sendTo(env, (EntityPlayerMP) target.arg);
                break;
            case "dimension":
                Object a = target.arg;
                int dim = a instanceof ResourceKey ? Dimensions.id((ResourceKey<?>) a) : a instanceof Integer ? (Integer) a : 0;
                wrapper.sendToDimension(env, dim);
                break;
            case "server":
                wrapper.sendToServer(env);
                break;
            default:
                wrapper.sendToAll(env);
        }
    }

    public <M> void sendTo(M msg, Object connection, Object direction) {
        if (connection instanceof EntityPlayerMP && net.mcreator.boh.compat.net.CompatNetwork.connected((EntityPlayerMP) connection))
            wrapper.sendTo(wrap(msg), (EntityPlayerMP) connection);
    }

    @SuppressWarnings("unchecked")
    void dispatch(Envelope env, Context ctx) {
        Codec<Object> c = (Codec<Object>) codecs.get(env.id);
        Object msg = c.decoder.apply(new FriendlyByteBuf(Unpooled.wrappedBuffer(env.payload)));
        c.handler.accept(msg, () -> ctx);
    }

    public static final class Envelope implements IMessage {

        String channel;
        int id;
        byte[] payload;

        public Envelope() {}

        @Override
        public void fromBytes(ByteBuf b) {
            byte[] n = new byte[b.readUnsignedByte()];
            b.readBytes(n);
            channel = new String(n, java.nio.charset.StandardCharsets.UTF_8);
            id = b.readUnsignedShort();
            payload = new byte[b.readInt()];
            b.readBytes(payload);
        }

        @Override
        public void toBytes(ByteBuf b) {
            byte[] n = channel.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            b.writeByte(n.length);
            b.writeBytes(n);
            b.writeShort(id);
            b.writeInt(payload.length);
            b.writeBytes(payload);
        }
    }

    public static final class ServerHandler implements IMessageHandler<Envelope, IMessage> {

        @Override
        public IMessage onMessage(Envelope m, MessageContext ctx) {
            SimpleChannel c = CHANNELS.get(m.channel);
            EntityPlayerMP p = ctx.getServerHandler().playerEntity;
            if (c != null) c.dispatch(m, new Context(p, NetworkDirection.PLAY_TO_SERVER));
            return null;
        }
    }

    public static final class ClientHandler implements IMessageHandler<Envelope, IMessage> {

        @Override
        public IMessage onMessage(Envelope m, MessageContext ctx) {
            SimpleChannel c = CHANNELS.get(m.channel);
            if (c != null) c.dispatch(m, new Context(null, NetworkDirection.PLAY_TO_CLIENT));
            return null;
        }
    }
}
