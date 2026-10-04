package net.mcreator.boh.compat.mc.network.protocol.game;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2BPacketChangeGameState;

public final class ClientboundGameEventPacket implements CompatPacket {
    public static final int NO_RESPAWN_BLOCK_AVAILABLE = 0;
    public static final int START_RAINING = 1;
    public static final int STOP_RAINING = 2;
    public static final int CHANGE_GAME_MODE = 3;
    public static final int WIN_GAME = 4;
    public static final int DEMO_EVENT = 5;
    public static final int ARROW_HIT_PLAYER = 6;
    public static final int RAIN_LEVEL_CHANGE = 7;
    public static final int THUNDER_LEVEL_CHANGE = 8;
    public static final int PUFFER_FISH_STING = 9;
    public static final int GUARDIAN_ELDER_EFFECT = 10;
    public static final int IMMEDIATE_RESPAWN = 11;
    private final int event;
    private final float param;

    public ClientboundGameEventPacket(int event, float param) {
        this.event = event;
        this.param = param;
    }

    @Override
    public void sendTo(EntityPlayerMP p) {
        if (this.event != 4 && this.event <= 8) {
            p.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(this.event, this.param));
        }
    }
}
