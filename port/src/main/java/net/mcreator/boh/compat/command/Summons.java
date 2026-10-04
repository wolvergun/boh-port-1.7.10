package net.mcreator.boh.compat.command;

import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
import net.mcreator.boh.compat.entity.BohLightningBolt;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** Entity creation for the summon command, including the 1.20 NBT forms the mod uses. */
public final class Summons {

    private Summons() {}

    public static Entity create(String id, World w, Vec3 p, String nbt) {
        NBTTagCompound tag = null;
        if (nbt != null && nbt.startsWith("{")) {
            try {
                NBTBase b = JsonToNBT.func_150315_a(nbt.replace("[I;", "["));
                if (b instanceof NBTTagCompound) tag = (NBTTagCompound) b;
            } catch (Exception ignored) {}
        }
        switch (id) {
            case "minecraft:lightning_bolt":
                return new BohLightningBolt(w, p.x, p.y, p.z);
            case "minecraft:firework_rocket": {
                ItemStack rocket = new ItemStack(Items.fireworks);
                if (tag != null && tag.hasKey("FireworksItem")) {
                    NBTTagCompound item = tag.getCompoundTag("FireworksItem");
                    if (item.hasKey("tag")) rocket.setTagCompound(item.getCompoundTag("tag"));
                }
                EntityFireworkRocket r = new EntityFireworkRocket(w, p.x, p.y, p.z, rocket);
                return r;
            }
            case "minecraft:area_effect_cloud":
                return BohAreaEffectCloud.fromNbt(w, p, tag);
            default: {
                EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id));
                if (type == null) return null;
                Entity e = type.create(w);
                if (e == null) return null;
                e.setLocationAndAngles(p.x, p.y, p.z, w.rand.nextFloat() * 360F, 0);
                if (e instanceof EntityLiving) ((EntityLiving) e).onSpawnWithEgg(null);
                return e;
            }
        }
    }

    /** 1.20 attribute ids -> 1.7.10 attribute names. */
    public static String attributeName(String id) {
        switch (id.replace("minecraft:", "")) {
            case "generic.max_health":
                return "generic.maxHealth";
            case "generic.movement_speed":
                return "generic.movementSpeed";
            case "generic.attack_damage":
                return "generic.attackDamage";
            case "generic.follow_range":
                return "generic.followRange";
            case "generic.knockback_resistance":
                return "generic.knockbackResistance";
            case "generic.armor":
                return "boh.armor";
            case "generic.flying_speed":
                return "boh.flyingSpeed";
            case "generic.attack_knockback":
                return "boh.attackKnockback";
            default:
                return id;
        }
    }
}
