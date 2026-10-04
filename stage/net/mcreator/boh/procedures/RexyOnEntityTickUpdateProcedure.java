package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.RexyEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class RexyOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "rexy_roar") && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "rexy_roar", true);
            }
            if (Math.random() < 0.1 && !M.getBoolean(M.getPersistentData(entity), "twitch") && M.getBoolean(M.getPersistentData(entity), "rexy_roar")) {
                if ((entity instanceof RexyEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(RexyEntity.DATA_Variant) : 0) == 0) {
                    if (entity instanceof RexyEntity) {
                        ((RexyEntity) entity).setAnimation("roar");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar")), SoundSource.HOSTILE, 3.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                        }
                    }
                } else if ((entity instanceof RexyEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(RexyEntity.DATA_Variant) : 0) == 1) {
                    if (entity instanceof RexyEntity) {
                        ((RexyEntity) entity).setAnimation("roar2");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar_novel")), SoundSource.HOSTILE, 3.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar_novel")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                        }
                    }
                } else if ((entity instanceof RexyEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(RexyEntity.DATA_Variant) : 0) == 2) {
                    if (entity instanceof RexyEntity) {
                        ((RexyEntity) entity).setAnimation("roar2");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rex_roar")), SoundSource.HOSTILE, 3.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rex_roar")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                        }
                    }
                }
                M.putBoolean(M.getPersistentData(entity), "twitch", true);
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 140, 254, false, false));
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 140, 254, false, false));
                }
            }
            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "rexy_roar", false);
                M.putBoolean(M.getPersistentData(entity), "twitch", false);
            }
            if (M.getDeltaMovement(entity).horizontalDistanceSqr() > 1.0E-6) {
                M.putDouble(M.getPersistentData(entity), "timer_step", M.getDouble(M.getPersistentData(entity), "timer_step") + 1.0);
            } else {
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (entity instanceof EntityLiving _mobx && M.isAggressive(_mobx) && M.getDouble(M.getPersistentData(entity), "timer_step") == 9.0) {
                    if (!M.isClientSide(world) && world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 3.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                        }
                    }
                    M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
                }
            } else if (M.getDouble(M.getPersistentData(entity), "timer_step") == 28.0) {
                if (!M.isClientSide(world) && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 3.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }
            if (!(entity instanceof EntityLiving _mob && M.isAggressive(_mob)) && !(world instanceof World _lvl37 && M.isDay(_lvl37))) {
                M.setShiftKeyDown(entity, true);
            }
            if (!(entity instanceof EntityLiving _mob && M.isAggressive(_mob)) && world instanceof World _lvl40 && M.isDay(_lvl40)) {
                M.setShiftKeyDown(entity, false);
            }
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob) && !(world instanceof World _lvl43 && M.isDay(_lvl43))) {
                M.setShiftKeyDown(entity, false);
            }
            if (M.isShiftKeyDown(entity)) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 20, 254, false, false));
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                }
            }
            if ((entity instanceof RexyEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(RexyEntity.DATA_Variant) : 0) == 2) {
                M.setCustomName(entity, Component.literal("Rex"));
            }
        }
    }
}
