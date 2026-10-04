package net.mcreator.boh.compat.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/** Opens 1.20-style menus (menu type id + window id + extra data) on the client. */
public final class MenuNetwork {

    private static SimpleNetworkWrapper channel;

    private MenuNetwork() {}

    public static void init() {
        if (channel != null) return;
        channel = NetworkRegistry.INSTANCE.newSimpleChannel("boh_menu");
        channel.registerMessage(OpenMsg.Handler.class, OpenMsg.class, 0, Side.CLIENT);
    }

    public static void sendOpen(EntityPlayerMP p, MenuType<?> type, int windowId, String title, byte[] data) {
        OpenMsg m = new OpenMsg();
        m.type = type.getId() == null ? "" : type.getId().toString();
        m.windowId = windowId;
        m.title = title;
        m.data = data;
        if (CompatNetwork.connected(p)) channel.sendTo(m, p);
    }

    public static final class OpenMsg implements IMessage {

        String type, title;
        int windowId;
        byte[] data;

        public OpenMsg() {}

        @Override
        public void fromBytes(ByteBuf b) {
            type = ByteBufUtils.readUTF8String(b);
            windowId = b.readInt();
            title = ByteBufUtils.readUTF8String(b);
            data = new byte[b.readInt()];
            b.readBytes(data);
        }

        @Override
        public void toBytes(ByteBuf b) {
            ByteBufUtils.writeUTF8String(b, type);
            b.writeInt(windowId);
            ByteBufUtils.writeUTF8String(b, title);
            b.writeInt(data.length);
            b.writeBytes(data);
        }

        public static final class Handler implements IMessageHandler<OpenMsg, IMessage> {

            @Override
            public IMessage onMessage(OpenMsg m, MessageContext ctx) {
                net.mcreator.boh.compat.client.MenuScreensImpl.open(m.type, m.windowId, m.title,
                    new FriendlyByteBuf(Unpooled.wrappedBuffer(m.data)));
                return null;
            }
        }
    }
}
