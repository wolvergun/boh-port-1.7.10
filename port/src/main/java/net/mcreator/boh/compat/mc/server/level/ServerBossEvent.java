package net.mcreator.boh.compat.mc.server.level;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.BossBarColor;
import net.mcreator.boh.compat.mc.world.BossBarOverlay;
import net.mcreator.boh.compat.net.CompatNetwork;
import net.minecraft.entity.player.EntityPlayerMP;

/** 1.20 ServerBossEvent: a boss bar shown to the players added to it (drawn by the client overlay). */
public class ServerBossEvent {

    private final UUID id = UUID.randomUUID();
    private final Set<EntityPlayerMP> players = new HashSet<>();
    private Component name;
    private final BossBarColor color;
    private final BossBarOverlay overlay;
    private float progress = 1.0F;
    private boolean visible = true;

    public ServerBossEvent(Component name, BossBarColor color, BossBarOverlay overlay) {
        this.name = name;
        this.color = color;
        this.overlay = overlay;
    }

    public void addPlayer(EntityPlayerMP p) {
        if (players.add(p)) send(p, true);
    }

    public void removePlayer(EntityPlayerMP p) {
        if (players.remove(p)) CompatNetwork.sendBossBar(p, id, null, 0, 0, 0, false);
    }

    public void removeAllPlayers() {
        for (EntityPlayerMP p : new HashSet<>(players)) removePlayer(p);
    }

    public Set<EntityPlayerMP> getPlayers() {
        return players;
    }

    public void setProgress(float progress) {
        if (Math.abs(progress - this.progress) < 0.001F) return;
        this.progress = progress;
        for (EntityPlayerMP p : players) send(p, true);
    }

    public float getProgress() {
        return progress;
    }

    public void setName(Component name) {
        this.name = name;
        for (EntityPlayerMP p : players) send(p, true);
    }

    public void setVisible(boolean v) {
        visible = v;
        for (EntityPlayerMP p : players) send(p, v);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setDarkenScreen(boolean b) {}

    public void setPlayBossMusic(boolean b) {}

    public void setCreateWorldFog(boolean b) {}

    private void send(EntityPlayerMP p, boolean show) {
        CompatNetwork.sendBossBar(p, id, name == null ? "" : name.getFormattedText(), progress, color.rgb, overlay.notches, show && visible);
    }
}
