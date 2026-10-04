package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.PredatorEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
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

public class PredatorOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.getBoolean(M.getPersistentData(entity), "predator_cloak_anim") && entity instanceof EntityLiving _mob && M.isAggressive(_mob) && !M.getBoolean(M.getPersistentData(entity), "predator_cloak") && Math.random() < 0.1) {
                M.putBoolean(M.getPersistentData(entity), "predator_cloak_anim", true);
            }
            if (M.getBoolean(M.getPersistentData(entity), "predator_cloak") && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 20, 1, false, false));
            }
            if (M.getBoolean(M.getPersistentData(entity), "predator_cloak_anim")) {
                M.putBoolean(M.getPersistentData(entity), "predator_cloak_anim", false);
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_cloak")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_cloak")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                if (entity instanceof PredatorEntity) {
                    ((PredatorEntity) entity).setAnimation("cloak");
                }
                BohMod.queueServerWork(20, () -> M.putBoolean(M.getPersistentData(entity), "predator_cloak", true));
                BohMod.queueServerWork(100, () -> {
                    if (world instanceof World _levelx) {
                        if (!M.isClientSide(_levelx)) {
                            M.playSound(_levelx, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_decloak_water")), SoundSource.HOSTILE, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_levelx, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_decloak_water")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                        }
                    }
                    M.putBoolean(M.getPersistentData(entity), "predator_cloak", false);
                });
            }
            if (M.getBoolean(M.getPersistentData(entity), "predator_cloak") && M.isInWaterRainOrBubble(entity)) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_decloak_water")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_decloak_water")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                M.putBoolean(M.getPersistentData(entity), "predator_cloak", false);
            }
        }
    }
}
