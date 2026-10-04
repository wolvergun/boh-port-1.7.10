package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;

public interface CompatPacket {
    void sendTo(EntityPlayerMP var1);
}
