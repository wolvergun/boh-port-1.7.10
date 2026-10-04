package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class NothingThereOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "radio_static", M.getDouble(M.getPersistentData(entity), "radio_static") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "radio_static") == 50.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_idle")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_idle")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "radio_static", 0.0);
            }
            if (!M.getBoolean(M.getPersistentData(entity), "lines_michael") && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", true);
            }
            if (!M.getBoolean(M.getPersistentData(entity), "throlgular") && M.getBoolean(M.getPersistentData(entity), "lines_michael")) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_aggro")), SoundSource.HOSTILE, 2.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_aggro")), SoundSource.HOSTILE, 2.0F, 1.0F, false);
                    }
                }
                M.putBoolean(M.getPersistentData(entity), "throlgular", true);
            }
            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", false);
                M.putBoolean(M.getPersistentData(entity), "throlgular", false);
            }
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (entity instanceof EntityLiving _mobx && M.isAggressive(_mobx) && M.getDouble(M.getPersistentData(entity), "timer_step") == 12.0) {
                    if (!M.isClientSide(world) && world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")), SoundSource.HOSTILE, 3.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                        }
                    }
                    M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
                }
            } else if (M.getDouble(M.getPersistentData(entity), "timer_step") == 12.0) {
                if (!M.isClientSide(world) && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")), SoundSource.HOSTILE, 3.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        boolean _setval = true;
                        M.getCapability(entityiterator, BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.chase_nothingthere = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            }
            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) && world instanceof WorldServer _level) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/stopsound @a music boh:chase_nothing_there");
            }
            if (M.getDeltaMovement(entity).horizontalDistanceSqr() > 1.0E-6) {
                M.putDouble(M.getPersistentData(entity), "timer_step", M.getDouble(M.getPersistentData(entity), "timer_step") + 1.0);
            } else {
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }
        }
    }
}
