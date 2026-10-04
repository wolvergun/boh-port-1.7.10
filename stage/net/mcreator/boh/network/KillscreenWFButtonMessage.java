package net.mcreator.boh.network;

import java.util.HashMap;
import java.util.function.Supplier;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.procedures.ClickNOProcedure;
import net.mcreator.boh.procedures.ClickYesProcedure;
import net.mcreator.boh.world.inventory.KillscreenWFMenu;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.forge.network.Context;
import net.mcreator.boh.compat.M;

public class KillscreenWFButtonMessage {

    private final int buttonID;

    private final int x;

    private final int y;

    private final int z;

    public KillscreenWFButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = M.readInt(buffer);
        this.x = M.readInt(buffer);
        this.y = M.readInt(buffer);
        this.z = M.readInt(buffer);
    }

    public KillscreenWFButtonMessage(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void buffer(KillscreenWFButtonMessage message, FriendlyByteBuf buffer) {
        M.writeInt(buffer, message.buttonID);
        M.writeInt(buffer, message.x);
        M.writeInt(buffer, message.y);
        M.writeInt(buffer, message.z);
    }

    public static void handler(KillscreenWFButtonMessage message, Supplier<Context> contextSupplier) {
        Context context = contextSupplier.get();
        M.enqueueWork(context, () -> {
            EntityPlayer entity = M.getSender(context);
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            handleButtonAction(entity, buttonID, x, y, z);
        });
        M.setPacketHandled(context, true);
    }

    public static void handleButtonAction(EntityPlayer entity, int buttonID, int x, int y, int z) {
        World world = M.level(entity);
        HashMap guistate = KillscreenWFMenu.guistate;
        if (M.hasChunkAt(world, new BlockPos(x, y, z))) {
            if (buttonID == 0) {
                ClickYesProcedure.execute(world, entity);
            }
            if (buttonID == 1) {
                ClickNOProcedure.execute(entity);
            }
        }
    }

    @SubscribeEvent
    public void registerMessage(FMLCommonSetupEvent event) {
        BohMod.addNetworkMessage(KillscreenWFButtonMessage.class, KillscreenWFButtonMessage::buffer, KillscreenWFButtonMessage::new, KillscreenWFButtonMessage::handler);
    }
}
