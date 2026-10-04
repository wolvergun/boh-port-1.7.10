package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.MothmanEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MothmanEntityIsHurtProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && Math.random() < 0.1) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")), SoundSource.HOSTILE, 1.0F, 1.0F, false
                    );
                }
            }

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
                    "/particle minecraft:squid_ink ~ ~ ~ 0.5 .5 0.5 0 100"
                );
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 20, 254, false, false));
            }

            if (entity instanceof MothmanEntity) {
                ((MothmanEntity)entity).setAnimation("flight");
            }

            BohMod.queueServerWork(
                20,
                () -> {
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
                            "/spreadplayers ~ ~ 30 30 false @e[type=boh:mothman,limit=1,sort=nearest]"
                        );
                    }

                    if (entity instanceof MothmanEntity) {
                        ((MothmanEntity)entity).setAnimation("landing");
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    BohMod.queueServerWork(
                        12,
                        () -> {
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
                                    "/particle minecraft:squid_ink ~ ~ ~ 1 .1 1 0 100"
                                );
                            }
                        }
                    );
                }
            );
        }
    }
}
