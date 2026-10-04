package net.mcreator.boh.compat.mc.world.effect;

import net.mcreator.boh.compat.effect.ExtraEffects;
import net.minecraft.potion.Potion;

public final class MobEffects {
    public static final Potion MOVEMENT_SPEED = Potion.moveSpeed;
    public static final Potion MOVEMENT_SLOWDOWN = Potion.moveSlowdown;
    public static final Potion DIG_SPEED = Potion.digSpeed;
    public static final Potion DIG_SLOWDOWN = Potion.digSlowdown;
    public static final Potion DAMAGE_BOOST = Potion.damageBoost;
    public static final Potion HEAL = Potion.heal;
    public static final Potion HARM = Potion.harm;
    public static final Potion JUMP = Potion.jump;
    public static final Potion CONFUSION = Potion.confusion;
    public static final Potion REGENERATION = Potion.regeneration;
    public static final Potion DAMAGE_RESISTANCE = Potion.resistance;
    public static final Potion FIRE_RESISTANCE = Potion.fireResistance;
    public static final Potion WATER_BREATHING = Potion.waterBreathing;
    public static final Potion INVISIBILITY = Potion.invisibility;
    public static final Potion BLINDNESS = Potion.blindness;
    public static final Potion NIGHT_VISION = Potion.nightVision;
    public static final Potion HUNGER = Potion.hunger;
    public static final Potion WEAKNESS = Potion.weakness;
    public static final Potion POISON = Potion.poison;
    public static final Potion WITHER = Potion.wither;
    public static final Potion HEALTH_BOOST = Potion.field_76434_w;
    public static final Potion ABSORPTION = Potion.field_76444_x;
    public static final Potion SATURATION = Potion.field_76443_y;
    public static final Potion GLOWING = ExtraEffects.glowing;
    public static final Potion LEVITATION = ExtraEffects.levitation;
    public static final Potion LUCK = ExtraEffects.luck;
    public static final Potion UNLUCK = ExtraEffects.luck;
    public static final Potion SLOW_FALLING = ExtraEffects.slowFalling;
    public static final Potion CONDUIT_POWER = ExtraEffects.conduitPower;
    public static final Potion DOLPHINS_GRACE = ExtraEffects.dolphinsGrace;
    public static final Potion BAD_OMEN = ExtraEffects.badOmen;
    public static final Potion HERO_OF_THE_VILLAGE = ExtraEffects.luck;
    public static final Potion DARKNESS = ExtraEffects.darkness;

    private MobEffects() {
    }

    static {
        ExtraEffects.init();
    }
}
