package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class RabbidRightClickedOnEntityProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (!M.isShiftKeyDown(sourceentity)) {
                if (M.getItem((sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == BohModItems.PRETZEL.get() && !(entity instanceof EntityTameable _tamIsTamedBy && sourceentity instanceof EntityLivingBase _livEnt && M.isOwnedBy(_tamIsTamedBy, _livEnt))) {
                    if (entity instanceof EntityTameable _toTame && sourceentity instanceof EntityPlayer _owner) {
                        M.tame(_toTame, _owner);
                    }
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:happy_villager ~ ~1.8 ~ 0.3 0.2 0.3 0 5");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")), SoundSource.AMBIENT, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        }
                    }
                }
                if (M.getItem((sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == M.getItem(M.EMPTY) && entity instanceof EntityTameable _tamIsTamedBy && sourceentity instanceof EntityLivingBase _livEnt && M.isOwnedBy(_tamIsTamedBy, _livEnt) && entity instanceof EntityTameable _tamIsTamedByx && sourceentity instanceof EntityLivingBase _livEntx && M.isOwnedBy(_tamIsTamedByx, _livEntx)) {
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")), SoundSource.PLAYERS, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                        }
                    }
                    if (M.getDouble(M.getPersistentData(entity), "state_ai") == 0.0) {
                        if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                            M.displayClientMessage(_player, Component.literal("Is Staying"), true);
                        }
                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 99999, 255, false, false));
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
}
