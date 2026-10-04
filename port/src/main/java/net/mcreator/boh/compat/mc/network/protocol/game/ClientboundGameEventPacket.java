package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2BPacketChangeGameState;

/** 1.20 ClientboundGameEventPacket. WIN_GAME (credits) is only sent by MCreator around dimension changes and is skipped. */
public final class ClientboundGameEventPacket implements CompatPacket {

    public static final int NO_RESPAWN_BLOCK_AVAILABLE = 0, START_RAINING = 1, STOP_RAINING = 2, CHANGE_GAME_MODE = 3, WIN_GAME = 4,
        DEMO_EVENT = 5, ARROW_HIT_PLAYER = 6, RAIN_LEVEL_CHANGE = 7, THUNDER_LEVEL_CHANGE = 8, PUFFER_FISH_STING = 9,
        GUARDIAN_ELDER_EFFECT = 10, IMMEDIATE_RESPAWN = 11;

    private final int event;
    private final float param;

    public ClientboundGameEventPacket(int event, float param) {
        this.event = event;
        this.param = param;
    }

    @Override
    public void sendTo(EntityPlayerMP p) {
        if (event == WIN_GAME || event > 8) return;
        p.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(event, param));
    }
}
