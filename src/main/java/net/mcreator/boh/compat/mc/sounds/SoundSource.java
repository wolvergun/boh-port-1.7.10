package net.mcreator.boh.compat.mc.sounds;

public enum SoundSource {
    MASTER,
    MUSIC,
    RECORDS,
    WEATHER,
    BLOCKS,
    HOSTILE,
    NEUTRAL,
    PLAYERS,
    AMBIENT,
    VOICE;

    public String getName() {
        return this.name().toLowerCase();
    }
}
