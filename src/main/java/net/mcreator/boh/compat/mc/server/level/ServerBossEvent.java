package net.mcreator.boh.compat.mc.server.level;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.BossBarColor;
import net.mcreator.boh.compat.mc.world.BossBarOverlay;
import net.mcreator.boh.compat.net.CompatNetwork;
import net.minecraft.entity.player.EntityPlayerMP;

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
        if (this.players.add(p)) {
            this.send(p, true);
        }
    }

    public void removePlayer(EntityPlayerMP p) {
        if (this.players.remove(p)) {
            CompatNetwork.sendBossBar(p, this.id, null, 0.0F, 0, 0, false);
        }
    }

    public void removeAllPlayers() {
        for (EntityPlayerMP p : new HashSet<>(this.players)) {
            this.removePlayer(p);
        }
    }

    public Set<EntityPlayerMP> getPlayers() {
        return this.players;
    }

    public void setProgress(float progress) {
        if (!(Math.abs(progress - this.progress) < 0.001F)) {
            this.progress = progress;

            for (EntityPlayerMP p : this.players) {
                this.send(p, true);
            }
        }
    }

    public float getProgress() {
        return this.progress;
    }

    public void setName(Component name) {
        this.name = name;

        for (EntityPlayerMP p : this.players) {
            this.send(p, true);
        }
    }

    public void setVisible(boolean v) {
        this.visible = v;

        for (EntityPlayerMP p : this.players) {
            this.send(p, v);
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setDarkenScreen(boolean b) {
    }

    public void setPlayBossMusic(boolean b) {
    }

    public void setCreateWorldFog(boolean b) {
    }

    private void send(EntityPlayerMP p, boolean show) {
        CompatNetwork.sendBossBar(
            p, this.id, this.name == null ? "" : this.name.getFormattedText(), this.progress, this.color.rgb, this.overlay.notches, show && this.visible
        );
    }
}
