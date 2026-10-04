package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MadOnEffectActiveTickProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "mad_timer", 1.0 + M.getDouble(M.getPersistentData(entity), "mad_timer"));
            if (M.getDouble(M.getPersistentData(entity), "mad_timer") == 600.0) {
                M.putDouble(M.getPersistentData(entity), "mad_timer", 0.0);
                if (Math.random() < 0.5 && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                        "effect give @s kurolib:bleeding 2 3"
                    );
                }

                M.hurt(
                    entity,
                    M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                    6.0F
                );
                if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("NOTHING IS WORTH THE RISK."), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("END YOURSELF."), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("I KNOW WHAT YOU DREAD, I KNOW WHAT YOU LOVE"), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("I KNOW EVERYTHING ABOUT WHAT MAKES YOU HUMAN."), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("I AM YOUR TRUE SAVIOR."), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("DO NOT BE AFRAID."), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("YOU ARE A FOOL."), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("YOUR EXISTENCE HAS MEANT NOTHING."), true);
                    }
                } else if (Math.random() < 0.2) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("I AM INSIDE YOUR HOME"), true);
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:knock")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:knock")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }
                } else {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("AN INTRUDER"), true);
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:knock")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:knock")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }
                }
            }
        }
    }
}
