package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;

public final class ClientboundPlayerAbilitiesPacket implements CompatPacket {
    public ClientboundPlayerAbilitiesPacket(Object abilities) {
    }

    @Override
    public void sendTo(EntityPlayerMP p) {
        p.sendPlayerAbilities();
    }
}
