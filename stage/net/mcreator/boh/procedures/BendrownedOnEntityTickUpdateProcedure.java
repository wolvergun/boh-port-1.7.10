package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BendrownedOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (Math.random() < 0.03 && Math.random() < 0.1 && entityiterator instanceof EntityPlayer) {
                    M.setSecondsOnFire(entityiterator, 45);
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute at @e[type=boh:bendrowned,distance=0..5] run playsound boh:ben_laughing hostile @p ~ ~ ~");
                    }
                    if (M.isClientSide(world) && world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_burning")), SoundSource.RECORDS, 2.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_burning")), SoundSource.RECORDS, 2.0F, 1.0F, false);
                        }
                    }
                }
            }
            Vec3 _center_r6 = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r6, _center_r6).inflate(5.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r6))).toList()) {
                if (Math.random() < 0.03 && Math.random() < 0.1 && entityiterator instanceof EntityPlayer) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute at @e[type=boh:bendrowned,distance=0..5] run playsound boh:ben_laughing hostile @p ~ ~ ~");
                    }
                    Entity _ent_r7 = entity;
                    if (!M.isClientSide(M.level(_ent_r7)) && M.getServer(_ent_r7) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r7)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r7), M.getRotationVector(_ent_r7), M.level(_ent_r7) instanceof WorldServer ? (WorldServer) M.level(_ent_r7) : null, 4, M.getString(M.getName(_ent_r7)), M.getDisplayName(_ent_r7), M.getServer(M.level(_ent_r7)), _ent_r7), "/spreadplayers ~ ~ 20 30 false @p");
                    }
                }
            }
            Vec3 _center_r8 = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r8, _center_r8).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r8))).toList()) {
                if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.DROWNING.get(), 20, 0, false, false));
                }
            }
            if (M.isInWater(entity)) {
                M.putBoolean(M.getPersistentData(entity), "inWater", true);
            }
            if (!M.isInWater(entity)) {
                M.putBoolean(M.getPersistentData(entity), "inWater", false);
            }
        }
    }
}
