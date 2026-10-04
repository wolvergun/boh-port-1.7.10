package net.mcreator.boh.compat.mc.world.damagesource;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

/** 1.20 damage type keys; {@link DamageSources} maps them onto 1.7.10 DamageSource ids. */
public final class DamageTypes {

    public static final ResourceKey<DamageType> IN_FIRE = key("in_fire");
    public static final ResourceKey<DamageType> LIGHTNING_BOLT = key("lightning_bolt");
    public static final ResourceKey<DamageType> ON_FIRE = key("on_fire");
    public static final ResourceKey<DamageType> LAVA = key("lava");
    public static final ResourceKey<DamageType> HOT_FLOOR = key("hot_floor");
    public static final ResourceKey<DamageType> IN_WALL = key("in_wall");
    public static final ResourceKey<DamageType> CRAMMING = key("cramming");
    public static final ResourceKey<DamageType> DROWN = key("drown");
    public static final ResourceKey<DamageType> STARVE = key("starve");
    public static final ResourceKey<DamageType> CACTUS = key("cactus");
    public static final ResourceKey<DamageType> FALL = key("fall");
    public static final ResourceKey<DamageType> FLY_INTO_WALL = key("fly_into_wall");
    public static final ResourceKey<DamageType> FELL_OUT_OF_WORLD = key("out_of_world");
    public static final ResourceKey<DamageType> GENERIC = key("generic");
    public static final ResourceKey<DamageType> MAGIC = key("magic");
    public static final ResourceKey<DamageType> WITHER = key("wither");
    public static final ResourceKey<DamageType> DRAGON_BREATH = key("dragon_breath");
    public static final ResourceKey<DamageType> DRY_OUT = key("dry_out");
    public static final ResourceKey<DamageType> SWEET_BERRY_BUSH = key("sweet_berry_bush");
    public static final ResourceKey<DamageType> FREEZE = key("freeze");
    public static final ResourceKey<DamageType> STALAGMITE = key("stalagmite");
    public static final ResourceKey<DamageType> FALLING_BLOCK = key("falling_block");
    public static final ResourceKey<DamageType> FALLING_ANVIL = key("falling_anvil");
    public static final ResourceKey<DamageType> FALLING_STALACTITE = key("falling_stalactite");
    public static final ResourceKey<DamageType> STING = key("sting");
    public static final ResourceKey<DamageType> MOB_ATTACK = key("mob_attack");
    public static final ResourceKey<DamageType> MOB_ATTACK_NO_AGGRO = key("mob_attack_no_aggro");
    public static final ResourceKey<DamageType> PLAYER_ATTACK = key("player_attack");
    public static final ResourceKey<DamageType> ARROW = key("arrow");
    public static final ResourceKey<DamageType> TRIDENT = key("trident");
    public static final ResourceKey<DamageType> MOB_PROJECTILE = key("mob_projectile");
    public static final ResourceKey<DamageType> FIREWORKS = key("fireworks");
    public static final ResourceKey<DamageType> FIREBALL = key("fireball");
    public static final ResourceKey<DamageType> UNATTRIBUTED_FIREBALL = key("unattributed_fireball");
    public static final ResourceKey<DamageType> WITHER_SKULL = key("wither_skull");
    public static final ResourceKey<DamageType> THROWN = key("thrown");
    public static final ResourceKey<DamageType> INDIRECT_MAGIC = key("indirect_magic");
    public static final ResourceKey<DamageType> THORNS = key("thorns");
    public static final ResourceKey<DamageType> EXPLOSION = key("explosion");
    public static final ResourceKey<DamageType> PLAYER_EXPLOSION = key("player_explosion");
    public static final ResourceKey<DamageType> SONIC_BOOM = key("sonic_boom");
    public static final ResourceKey<DamageType> BAD_RESPAWN_POINT = key("bad_respawn_point");
    public static final ResourceKey<DamageType> OUTSIDE_BORDER = key("outside_border");
    public static final ResourceKey<DamageType> GENERIC_KILL = key("generic_kill");

    private DamageTypes() {}

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft", name));
    }
}
