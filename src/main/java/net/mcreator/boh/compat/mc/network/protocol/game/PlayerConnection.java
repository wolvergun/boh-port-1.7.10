package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

public final class PlayerConnection {
    public final EntityPlayerMP player;

    public PlayerConnection(EntityPlayerMP player) {
        this.player = player;
    }

    public void send(Object packet) {
        if (packet instanceof CompatPacket) {
            ((CompatPacket)packet).sendTo(this.player);
        } else if (packet instanceof Packet) {
            this.player.playerNetServerHandler.sendPacket((Packet)packet);
        }
    }

    public void teleport(double x, double y, double z, float yaw, float pitch) {
        this.player.playerNetServerHandler.setPlayerLocation(x, y, z, yaw, pitch);
    }
}
