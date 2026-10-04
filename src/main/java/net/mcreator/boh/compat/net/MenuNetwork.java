package net.mcreator.boh.compat.net;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.mcreator.boh.compat.client.MenuScreensImpl;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.minecraft.entity.player.EntityPlayerMP;

public final class MenuNetwork {
    private static SimpleNetworkWrapper channel;

    private MenuNetwork() {
    }

    public static void init() {
        if (channel == null) {
            channel = NetworkRegistry.INSTANCE.newSimpleChannel("boh_menu");
            channel.registerMessage(MenuNetwork.OpenMsg.Handler.class, MenuNetwork.OpenMsg.class, 0, Side.CLIENT);
        }
    }

    public static void sendOpen(EntityPlayerMP p, MenuType<?> type, int windowId, String title, byte[] data) {
        MenuNetwork.OpenMsg m = new MenuNetwork.OpenMsg();
        m.type = type.getId() == null ? "" : type.getId().toString();
        m.windowId = windowId;
        m.title = title;
        m.data = data;
        channel.sendTo(m, p);
    }

    public static final class OpenMsg implements IMessage {
        String type;
        String title;
        int windowId;
        byte[] data;

        public void fromBytes(ByteBuf b) {
            this.type = ByteBufUtils.readUTF8String(b);
            this.windowId = b.readInt();
            this.title = ByteBufUtils.readUTF8String(b);
            this.data = new byte[b.readInt()];
            b.readBytes(this.data);
        }

        public void toBytes(ByteBuf b) {
            ByteBufUtils.writeUTF8String(b, this.type);
            b.writeInt(this.windowId);
            ByteBufUtils.writeUTF8String(b, this.title);
            b.writeInt(this.data.length);
            b.writeBytes(this.data);
        }

        public static final class Handler implements IMessageHandler<MenuNetwork.OpenMsg, IMessage> {
            public IMessage onMessage(MenuNetwork.OpenMsg m, MessageContext ctx) {
                MenuScreensImpl.open(m.type, m.windowId, m.title, new FriendlyByteBuf(Unpooled.wrappedBuffer(m.data)));
                return null;
            }
        }
    }
}
