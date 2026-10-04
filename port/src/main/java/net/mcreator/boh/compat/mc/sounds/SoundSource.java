package net.mcreator.boh.compat.mc.sounds;

/**
 * 1.20 sound categories. 1.7.10 takes the category from sounds.json instead, so this only carries the name
 * (and must stay free of client-only classes so the server can load it).
 */
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
        return name().toLowerCase();
    }
}
