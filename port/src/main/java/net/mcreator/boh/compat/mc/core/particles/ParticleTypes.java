package net.mcreator.boh.compat.mc.core.particles;

/** Vanilla particle types the mod spawns, mapped to 1.7.10 particle names. */
public final class ParticleTypes {

    public static final SimpleParticleType ASH = vanilla("townaura");
    public static final SimpleParticleType LARGE_SMOKE = vanilla("largesmoke");
    public static final SimpleParticleType SMOKE = vanilla("smoke");
    public static final SimpleParticleType SQUID_INK = vanilla("largesmoke");
    public static final SimpleParticleType SOUL = vanilla("depthsuspend");
    public static final SimpleParticleType EXPLOSION = vanilla("largeexplode");
    public static final SimpleParticleType EXPLOSION_EMITTER = vanilla("hugeexplosion");
    public static final SimpleParticleType FLAME = vanilla("flame");
    public static final SimpleParticleType HEART = vanilla("heart");
    public static final SimpleParticleType PORTAL = vanilla("portal");
    public static final SimpleParticleType CRIT = vanilla("crit");
    public static final SimpleParticleType ENCHANTED_HIT = vanilla("magicCrit");
    public static final SimpleParticleType CLOUD = vanilla("cloud");
    public static final SimpleParticleType POOF = vanilla("explode");
    public static final SimpleParticleType DRIPPING_LAVA = vanilla("dripLava");
    public static final SimpleParticleType DRIPPING_WATER = vanilla("dripWater");
    public static final SimpleParticleType WITCH = vanilla("witchMagic");
    public static final SimpleParticleType ENCHANT = vanilla("enchantmenttable");
    public static final SimpleParticleType NOTE = vanilla("note");
    public static final SimpleParticleType LAVA = vanilla("lava");
    public static final SimpleParticleType SPLASH = vanilla("splash");
    public static final SimpleParticleType BUBBLE = vanilla("bubble");
    public static final SimpleParticleType ANGRY_VILLAGER = vanilla("angryVillager");
    public static final SimpleParticleType HAPPY_VILLAGER = vanilla("happyVillager");
    public static final SimpleParticleType SOUL_FIRE_FLAME = vanilla("flame");
    public static final SimpleParticleType END_ROD = vanilla("fireworksSpark");
    public static final SimpleParticleType ITEM_SNOWBALL = vanilla("snowballpoof");

    private ParticleTypes() {}

    private static SimpleParticleType vanilla(String legacy) {
        SimpleParticleType t = new SimpleParticleType(false);
        t.withLegacyName(legacy);
        return t;
    }
}
