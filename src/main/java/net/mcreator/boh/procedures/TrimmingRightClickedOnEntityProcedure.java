package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TrimmingRightClickedOnEntityProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && !M.isShiftKeyDown(sourceentity)) {
            if (!(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))
                && M.getItem(sourceentity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY)
                    == M.asItem(BohModBlocks.VITA_CRAWL.get())
                && !(entity instanceof EntityTameable _tamIsTamedBy && sourceentity instanceof EntityLivingBase _livEnt && M.isOwnedBy(_tamIsTamedBy, _livEnt))
                )
             {
                if (!(entity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr))) && sourceentity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove;
                    ItemStack var40 = _stktoremove = sourceentity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY;
                    M.clearOrCountMatchingItems(
                        M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player))
                    );
                }

                if (Math.random() < 0.02) {
                    if (entity instanceof EntityTameable _toTame && sourceentity instanceof EntityPlayer _owner) {
                        M.tame(_toTame, _owner);
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
                            "/particle minecraft:happy_villager ~ ~1.8 ~ 0.3 0.2 0.3 0 5"
                        );
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                                SoundSource.AMBIENT,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                                SoundSource.AMBIENT,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }
                }
            }

            if ((entity instanceof EntityLivingBase _livEntxxx ? M.getHealth(_livEntxxx) : -1.0F)
                    < (entity instanceof EntityLivingBase _livEntxx ? M.getMaxHealth(_livEntxx) : -1.0F)
                && entity instanceof EntityTameable _tamEnt
                && M.isTame(_tamEnt)
                && M.getItem(sourceentity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY)
                    == M.asItem(BohModBlocks.VITA_CRAWL.get())
                && entity instanceof EntityTameable _tamIsTamedBy
                && sourceentity instanceof EntityLivingBase _livEnt
                && M.isOwnedBy(_tamIsTamedBy, _livEnt)) {
                if (sourceentity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove;
                    ItemStack var43 = _stktoremove = sourceentity instanceof EntityLivingBase _livEntxxxx ? M.getMainHandItem(_livEntxxxx) : M.EMPTY;
                    M.clearOrCountMatchingItems(
                        M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player))
                    );
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
                        "/particle minecraft:heart ~ ~.5 ~ 0.3 0.2 0.3 0 5"
                    );
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.HEAL, 10, 0, false, false));
                }
            }

            if (M.getItem(sourceentity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY) == M.getItem(M.EMPTY)
                && entity instanceof EntityTameable _tamIsTamedBy
                && sourceentity instanceof EntityLivingBase _livEnt
                && M.isOwnedBy(_tamIsTamedBy, _livEnt)
                && entity instanceof EntityTameable _tamIsTamedByx
                && sourceentity instanceof EntityLivingBase _livEntx
                && M.isOwnedBy(_tamIsTamedByx, _livEntx)) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                if (M.getDouble(M.getPersistentData(entity), "state_ai") == 0.0) {
                    if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("Is Staying"), true);
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 9999999, 255, false, false));
                    }
                } else if (M.getDouble(M.getPersistentData(entity), "state_ai") == 1.0) {
                    if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("Is Following"), true);
                    }

                    if (entity instanceof EntityLivingBase _entity) {
                        M.removeEffect(_entity, MobEffects.MOVEMENT_SLOWDOWN);
                    }
                }

                if (M.getDouble(M.getPersistentData(entity), "state_ai") <= 2.0) {
                    M.putDouble(M.getPersistentData(entity), "state_ai", M.getDouble(M.getPersistentData(entity), "state_ai") + 1.0);
                } else if (M.getDouble(M.getPersistentData(entity), "state_ai") >= 2.0) {
                    if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("Is Wandering"), true);
                    }

                    M.setShiftKeyDown(entity, false);
                    if (entity instanceof EntityLivingBase _entity) {
                        M.removeEffect(_entity, MobEffects.MOVEMENT_SLOWDOWN);
                    }

                    M.putDouble(M.getPersistentData(entity), "state_ai", 0.0);
                }
            }
        }
    }
}
