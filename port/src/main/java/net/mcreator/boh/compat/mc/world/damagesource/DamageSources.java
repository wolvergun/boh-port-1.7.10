package net.mcreator.boh.compat.mc.world.damagesource;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

/** Maps 1.20 damage type keys to and from 1.7.10 {@link DamageSource}s. */
public final class DamageSources {

    private static final Map<String, String> LEGACY = new HashMap<>();

    static {
        String[][] m = { { "in_fire", "inFire" }, { "on_fire", "onFire" }, { "lava", "lava" }, { "in_wall", "inWall" },
            { "drown", "drown" }, { "starve", "starve" }, { "cactus", "cactus" }, { "fall", "fall" },
            { "out_of_world", "outOfWorld" }, { "generic", "generic" }, { "magic", "magic" }, { "wither", "wither" },
            { "falling_anvil", "anvil" }, { "falling_block", "fallingBlock" }, { "mob_attack", "mob" },
            { "mob_attack_no_aggro", "mob" }, { "player_attack", "player" }, { "arrow", "arrow" },
            { "fireball", "fireball" }, { "unattributed_fireball", "fireball" }, { "thrown", "thrown" },
            { "indirect_magic", "indirectMagic" }, { "thorns", "thorns" }, { "explosion", "explosion" },
            { "player_explosion", "explosion.player" }, { "lightning_bolt", "lightningBolt" },
            { "wither_skull", "witherSkull" }, { "trident", "trident" }, { "dragon_breath", "dragonBreath" },
            { "sonic_boom", "sonic_boom" }, { "generic_kill", "generic" } };
        for (String[] p : m) LEGACY.put(p[0], p[1]);
    }

    private DamageSources() {}

    public static String legacyId(ResourceKey<?> key) {
        String path = key.location().getResourcePath();
        if (!"minecraft".equals(key.location().getResourceDomain())) return key.location().getResourceDomain() + "." + path;
        String l = LEGACY.get(path);
        return l != null ? l : path;
    }

    /** Whether a 1.7.10 damage source corresponds to the given 1.20 type. */
    public static boolean is(DamageSource src, ResourceKey<?> key) {
        if (src == null) return false;
        String path = key.location().getResourcePath();
        String type = src.getDamageType();
        switch (path) {
            case "explosion":
            case "player_explosion":
                return src.isExplosion();
            case "in_fire":
                return "inFire".equals(type);
            case "on_fire":
                return "onFire".equals(type);
            case "player_attack":
                return "player".equals(type);
            case "mob_attack":
                return "mob".equals(type);
            case "arrow":
                return "arrow".equals(type);
            case "lightning_bolt":
                return "lightningBolt".equals(type) || "inFire".equals(type) && src.getEntity() instanceof net.minecraft.entity.effect.EntityLightningBolt;
            case "wither_skull":
                return src.getSourceOfDamage() instanceof net.minecraft.entity.projectile.EntityWitherSkull;
            case "fireball":
                return src.isFireDamage() && src.isProjectile();
            default:
                return legacyId(key).equals(type);
        }
    }

    public static DamageSource create(ResourceKey<?> key) {
        return create(key, null, null);
    }

    public static DamageSource create(ResourceKey<?> key, Entity attacker) {
        return create(key, attacker, attacker);
    }

    public static DamageSource create(ResourceKey<?> key, Entity direct, Entity attacker) {
        String id = legacyId(key);
        switch (id) {
            case "inFire":
                return DamageSource.inFire;
            case "onFire":
                return DamageSource.onFire;
            case "lava":
                return DamageSource.lava;
            case "inWall":
                return DamageSource.inWall;
            case "drown":
                return DamageSource.drown;
            case "starve":
                return DamageSource.starve;
            case "cactus":
                return DamageSource.cactus;
            case "fall":
                return DamageSource.fall;
            case "outOfWorld":
                return DamageSource.outOfWorld;
            case "magic":
                return attacker != null ? new EntityDamageSourceIndirect("indirectMagic", direct, attacker).setDamageBypassesArmor().setMagicDamage() : DamageSource.magic;
            case "wither":
                return DamageSource.wither;
            case "anvil":
                return DamageSource.anvil;
            case "fallingBlock":
                return DamageSource.fallingBlock;
            case "mob":
                return attacker instanceof EntityLivingBase ? DamageSource.causeMobDamage((EntityLivingBase) attacker) : DamageSource.generic;
            case "player":
                return attacker instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) attacker) : DamageSource.generic;
            case "explosion":
            case "explosion.player":
                return new DamageSource(id).setExplosion();
            case "generic":
                return attacker != null ? new EntityDamageSource("generic", attacker) : DamageSource.generic;
            default:
                DamageSource d = attacker != null && direct != attacker ? new EntityDamageSourceIndirect(id, direct, attacker)
                    : attacker != null ? new EntityDamageSource(id, attacker) : new DamageSource(id);
                if (id.equals("lightningBolt")) d.setFireDamage();
                if (id.equals("dragonBreath") || id.equals("sonic_boom")) d.setDamageBypassesArmor().setMagicDamage();
                return d;
        }
    }
}
