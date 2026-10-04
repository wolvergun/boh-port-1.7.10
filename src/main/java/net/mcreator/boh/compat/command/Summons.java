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

public final class Summons {
    private Summons() {
    }

    public static Entity create(String id, World w, Vec3 p, String nbt) {
        NBTTagCompound tag = null;
        if (nbt != null && nbt.startsWith("{")) {
            try {
                NBTBase b = JsonToNBT.func_150315_a(nbt.replace("[I;", "["));
                if (b instanceof NBTTagCompound) {
                    tag = (NBTTagCompound)b;
                }
            } catch (Exception var9) {
            }
        }

        switch (id) {
            case "minecraft:lightning_bolt":
                return new BohLightningBolt(w, p.x, p.y, p.z);
            case "minecraft:firework_rocket":
                ItemStack rocket = new ItemStack(Items.fireworks);
                if (tag != null && tag.hasKey("FireworksItem")) {
                    NBTTagCompound item = tag.getCompoundTag("FireworksItem");
                    if (item.hasKey("tag")) {
                        rocket.setTagCompound(item.getCompoundTag("tag"));
                    }
                }

                return new EntityFireworkRocket(w, p.x, p.y, p.z, rocket);
            case "minecraft:area_effect_cloud":
                return BohAreaEffectCloud.fromNbt(w, p, tag);
            default:
                EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id));
                if (type == null) {
                    return null;
                } else {
                    Entity e = type.create(w);
                    if (e == null) {
                        return null;
                    } else {
                        e.setLocationAndAngles(p.x, p.y, p.z, w.rand.nextFloat() * 360.0F, 0.0F);
                        if (e instanceof EntityLiving) {
                            ((EntityLiving)e).onSpawnWithEgg(null);
                        }

                        return e;
                    }
                }
        }
    }

    public static String attributeName(String id) {
        String var1 = id.replace("minecraft:", "");
        switch (var1) {
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
