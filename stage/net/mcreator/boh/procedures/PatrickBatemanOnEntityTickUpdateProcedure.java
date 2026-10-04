package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class PatrickBatemanOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob) && Math.random() < 0.2 && Math.random() < 0.2 && Math.random() < 0.2 && M.getDeltaMovement(entity).horizontalDistanceSqr() > 1.0E-6 && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 60, 0, false, false));
            }
            if (entity instanceof EntityLivingBase _livEnt3 && M.hasEffect(_livEnt3, MobEffects.DAMAGE_BOOST)) {
                M.putBoolean(M.getPersistentData(entity), "trigger", true);
                if (entity instanceof PatrickBatemanEntity) {
                    ((PatrickBatemanEntity) entity).setAnimation("lunge");
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
                }
                BohMod.queueServerWork(20, () -> {
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 255, false, false));
                    }
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 100, 255, false, false));
                    }
                    if (entity instanceof EntityLivingBase _entity) {
                        M.removeEffect(_entity, MobEffects.DAMAGE_BOOST);
                    }
                    M.putBoolean(M.getPersistentData(entity), "trigger", false);
                    M.putBoolean(M.getPersistentData(entity), "line", false);
                });
            }
            if (M.getBoolean(M.getPersistentData(entity), "trigger") && entity instanceof EntityLivingBase _livEnt14 && M.hasEffect(_livEnt14, MobEffects.DAMAGE_BOOST)) {
                M.putBoolean(M.getPersistentData(entity), "line", true);
                if (world instanceof World _level && M.isClientSide(_level)) {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bateman_attack")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
            if (M.getBoolean(M.getPersistentData(entity), "line")) {
                M.putBoolean(M.getPersistentData(entity), "line", false);
            }
        }
    }
}
