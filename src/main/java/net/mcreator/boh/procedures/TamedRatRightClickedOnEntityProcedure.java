package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;

public class TamedRatRightClickedOnEntityProcedure {
    public static void execute(Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (!(entity instanceof EntityTameable _tamIsTamedBy && sourceentity instanceof EntityLivingBase _livEnt && M.isOwnedBy(_tamIsTamedBy, _livEnt))) {
                if (entity instanceof EntityTameable _toTame && sourceentity instanceof EntityPlayer _owner) {
                    M.tame(_toTame, _owner);
                }

                if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                    M.displayClientMessage(_player, Component.literal("Will now follow you"), true);
                }
            }

            EntityLivingBase _tamIsTamedByx = entity instanceof EntityTameable _tamEnt ? M.getOwner(_tamEnt) : null;
            if (M.getItem(_tamIsTamedByx instanceof EntityLivingBase ? M.getMainHandItem(_tamIsTamedByx) : M.EMPTY) == M.getItem(M.EMPTY)
                && entity instanceof EntityTameable _tamIsTamedByxx
                && sourceentity instanceof EntityLivingBase _livEntx
                && M.isOwnedBy(_tamIsTamedByxx, _livEntx)) {
                if (M.getDouble(M.getPersistentData(entity), "state_ai") == 0.0) {
                    if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("Is Staying"), true);
                    }

                    M.setShiftKeyDown(entity, true);
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 999999, 255, false, false));
                    }
                } else if (M.getDouble(M.getPersistentData(entity), "state_ai") == 1.0) {
                    if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("Is Following"), true);
                    }

                    M.setShiftKeyDown(entity, false);
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

            if ((entity instanceof EntityLivingBase _livEntxxxx ? M.getHealth(_livEntxxxx) : -1.0F)
                    < (entity instanceof EntityLivingBase _livEntxxx ? M.getMaxHealth(_livEntxxx) : -1.0F)
                && entity instanceof EntityTameable _tamEntx
                && M.isTame(_tamEntx)
                && M.getItem(sourceentity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY) == BohModItems.PRETZEL.get()
                && entity instanceof EntityTameable _tamIsTamedByxx
                && sourceentity instanceof EntityLivingBase _livEntx
                && M.isOwnedBy(_tamIsTamedByxx, _livEntx)) {
                if (sourceentity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove;
                    ItemStack var30 = _stktoremove = sourceentity instanceof EntityLivingBase _livEntxxxxx ? M.getMainHandItem(_livEntxxxxx) : M.EMPTY;
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
        }
    }
}
