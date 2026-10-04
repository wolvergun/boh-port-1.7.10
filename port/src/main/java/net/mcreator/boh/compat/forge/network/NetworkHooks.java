package net.mcreator.boh.compat.forge.network;

import java.util.function.Consumer;

import io.netty.buffer.Unpooled;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.inventory.ChestMenu;
import net.mcreator.boh.compat.net.MenuNetwork;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

/** Forge NetworkHooks.openScreen: opens the menu server-side and tells the client to build menu + screen. */
public final class NetworkHooks {

    private NetworkHooks() {}

    public static void openScreen(EntityPlayerMP player, MenuProvider provider) {
        openScreen(player, provider, buf -> {});
    }

    public static void openScreen(EntityPlayerMP player, MenuProvider provider, BlockPos pos) {
        openScreen(player, provider, buf -> buf.writeBlockPos(pos));
    }

    public static void openScreen(EntityPlayerMP player, MenuProvider provider, Consumer<FriendlyByteBuf> extra) {
        if (player.worldObj.isRemote) return;
        player.getNextWindowId();
        player.closeContainer();
        int windowId = player.currentWindowId;
        Container c = provider.createMenu(windowId, player.inventory, player);
        if (c == null) return;
        if (c instanceof ChestMenu) {
            player.displayGUIChest(((ChestMenu) c).container);
            return;
        }
        if (!(c instanceof AbstractContainerMenu)) return;
        AbstractContainerMenu menu = (AbstractContainerMenu) c;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        extra.accept(buf);
        byte[] data = new byte[buf.unwrap().readableBytes()];
        buf.unwrap().readBytes(data);
        MenuNetwork.sendOpen(player, menu.getType(), windowId, provider.getDisplayName().getFormattedText(), data);
        player.openContainer = menu;
        menu.windowId = windowId;
        menu.containerId = windowId;
        menu.addCraftingToCrafters(player);
    }
}
