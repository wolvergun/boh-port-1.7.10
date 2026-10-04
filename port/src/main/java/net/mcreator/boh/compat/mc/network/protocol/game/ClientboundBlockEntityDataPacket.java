package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.tileentity.TileEntity;

/** 1.20 ClientboundBlockEntityDataPacket; 1.7.10 tiles sync through getDescriptionPacket instead. */
public final class ClientboundBlockEntityDataPacket {

    private ClientboundBlockEntityDataPacket() {}

    public static ClientboundBlockEntityDataPacket create(TileEntity te) {
        return null;
    }
}
