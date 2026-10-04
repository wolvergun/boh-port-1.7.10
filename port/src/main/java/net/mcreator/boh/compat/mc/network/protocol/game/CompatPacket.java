package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;

/** A 1.20 clientbound packet translated to its 1.7.10 effect. */
public interface CompatPacket {

    void sendTo(EntityPlayerMP player);
}
