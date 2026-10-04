package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;

public final class ClientboundUpdateMobEffectPacket implements CompatPacket {
    private final int entityId;
    private final PotionEffect effect;

    public ClientboundUpdateMobEffectPacket(int entityId, PotionEffect effect) {
        this.entityId = entityId;
        this.effect = effect;
    }

    @Override
    public void sendTo(EntityPlayerMP p) {
        p.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.entityId, this.effect));
    }
}
