package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.M;

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
            if (M.getItem(((entity instanceof EntityTameable _tamEnt ? M.getOwner(_tamEnt) : null) instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY)) == M.getItem(M.EMPTY) && entity instanceof EntityTameable _tamIsTamedByx && sourceentity instanceof EntityLivingBase _livEntx && M.isOwnedBy(_tamIsTamedByx, _livEntx)) {
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
            if ((entity instanceof EntityLivingBase _livEntx ? M.getHealth(_livEntx) : -1.0F) < (entity instanceof EntityLivingBase _livEntx ? M.getMaxHealth(_livEntx) : -1.0F) && entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt) && M.getItem((sourceentity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY)) == BohModItems.PRETZEL.get() && entity instanceof EntityTameable _tamIsTamedByx && sourceentity instanceof EntityLivingBase _livEntx && M.isOwnedBy(_tamIsTamedByx, _livEntx)) {
                if (sourceentity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove;
                    ItemStack var32 = _stktoremove = sourceentity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY;
                    M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
                }
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:heart ~ ~.5 ~ 0.3 0.2 0.3 0 5");
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.HEAL, 10, 0, false, false));
                }
            }
        }
    }
}
