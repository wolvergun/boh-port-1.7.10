package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;

/** 1.20 ServerGamePacketListenerImpl (player.connection): sends the few clientbound packets the mod builds. */
public final class PlayerConnection {

    public final EntityPlayerMP player;

    public PlayerConnection(EntityPlayerMP player) {
        this.player = player;
    }

    public void send(Object packet) {
        if (packet instanceof CompatPacket) ((CompatPacket) packet).sendTo(player);
        else if (packet instanceof net.minecraft.network.Packet) player.playerNetServerHandler.sendPacket((net.minecraft.network.Packet) packet);
    }

    public void teleport(double x, double y, double z, float yaw, float pitch) {
        player.playerNetServerHandler.setPlayerLocation(x, y, z, yaw, pitch);
    }
}
