package net.mcreator.boh.compat.mc.network.protocol.game;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;

public final class ClientboundLevelEventPacket implements CompatPacket {
    private final int type;
    private final int data;
    private final BlockPos pos;
    private final boolean global;

    public ClientboundLevelEventPacket(int type, BlockPos pos, int data, boolean global) {
        this.type = type;
        this.pos = pos;
        this.data = data;
        this.global = global;
    }

    @Override
    public void sendTo(EntityPlayerMP p) {
        if (this.type == 1032) {
            p.playerNetServerHandler
                .sendPacket(
                    new S29PacketSoundEffect(
                        "portal.travel", p.posX, p.posY, p.posZ, 0.25F, p.worldObj.rand.nextFloat() * 0.4F + 0.8F
                    )
                );
        } else if (this.type < 2010) {
            p.playerNetServerHandler.sendPacket(new S28PacketEffect(this.type, this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.data, this.global));
        }
    }
}
