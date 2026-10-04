package net.mcreator.boh.compat.command;

import java.util.HashMap;
import java.util.Map;

/** 1.20 particle ids -> nearest 1.7.10 particle names. */
public final class ParticleNames {

    private static final Map<String, String> MAP = new HashMap<>();

    static {
        String[][] m = { { "sweep_attack", "magicCrit" }, { "squid_ink", "largesmoke" }, { "poof", "explode" }, { "heart", "heart" },
            { "happy_villager", "happyVillager" }, { "angry_villager", "angryVillager" }, { "smoke", "smoke" },
            { "large_smoke", "largesmoke" }, { "flame", "flame" }, { "soul_fire_flame", "flame" }, { "soul", "depthsuspend" },
            { "explosion", "largeexplode" }, { "explosion_emitter", "hugeexplosion" }, { "crit", "crit" },
            { "enchanted_hit", "magicCrit" }, { "portal", "portal" }, { "witch", "witchMagic" }, { "note", "note" },
            { "cloud", "cloud" }, { "lava", "lava" }, { "splash", "splash" }, { "bubble", "bubble" },
            { "dripping_water", "dripWater" }, { "dripping_lava", "dripLava" }, { "enchant", "enchantmenttable" },
            { "ash", "townaura" }, { "white_ash", "townaura" }, { "end_rod", "fireworksSpark" }, { "firework", "fireworksSpark" },
            { "effect", "spell" }, { "entity_effect", "mobSpell" }, { "instant_effect", "instantSpell" },
            { "damage_indicator", "crit" }, { "item_snowball", "snowballpoof" }, { "snowflake", "snowshovel" },
            { "campfire_cosy_smoke", "largesmoke" }, { "electric_spark", "fireworksSpark" }, { "glow", "happyVillager" },
            { "sonic_boom", "largeexplode" }, { "sculk_soul", "depthsuspend" }, { "falling_water", "dripWater" },
            { "landing_lava", "lava" }, { "mycelium", "townaura" } };
        for (String[] p : m) MAP.put(p[0], p[1]);
    }

    private ParticleNames() {}

    public static String legacy(String modern) {
        String n = modern.startsWith("minecraft:") ? modern.substring(10) : modern;
        String l = MAP.get(n);
        return l != null ? l : n;
    }
}
