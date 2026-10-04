package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LightHeadOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(entity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(entity),
                        M.getRotationVector(entity),
                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                        4,
                        M.getString(M.getName(entity)),
                        M.getDisplayName(entity),
                        M.getServer(M.level(entity)),
                        entity
                    ),
                    "/fill ~-1 ~ ~-1 ~1 ~9 ~1 air replace #minecraft:leaves"
                );
            }

            if (M.getDeltaMovement(entity).horizontalDistanceSqr() > 1.0E-6) {
                M.putDouble(M.getPersistentData(entity), "timer_step", M.getDouble(M.getPersistentData(entity), "timer_step") + 1.0);
            } else {
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }

            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (entity instanceof EntityLiving _mobx && M.isAggressive(_mobx) && M.getDouble(M.getPersistentData(entity), "timer_step") == 9.0) {
                    if (!M.isClientSide(world) && world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                                SoundSource.HOSTILE,
                                2.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                                SoundSource.HOSTILE,
                                2.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
                }
            } else if (M.getDouble(M.getPersistentData(entity), "timer_step") == 22.0) {
                if (!M.isClientSide(world) && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }
        }
    }
}
