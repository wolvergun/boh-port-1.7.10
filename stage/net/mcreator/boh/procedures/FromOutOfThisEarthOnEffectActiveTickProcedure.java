package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModParticleTypes;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class FromOutOfThisEarthOnEffectActiveTickProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityVillager) {
                if (M.getNearestPlayer(world, x, y, z, 5.0, true) != null && Math.random() < 0.1) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }
                    if (world instanceof WorldServer _level) {
                        M.sendParticles(_level, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity), M.getY(entity) + M.getBbHeight(entity) / 2.0F, M.getZ(entity), 20, 0.5, M.getBbHeight(entity) / 2.0F, 0.5, 0.02);
                    }
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.THE_THING_VILLAGER.get()), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, M.getX(entity), M.getY(entity), M.getZ(entity), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chestburster_kill")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
            } else if (entity instanceof EntityWolf && M.getNearestPlayer(world, x, y, z, 5.0, true) != null && Math.random() < 0.1) {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
                if (world instanceof WorldServer _level) {
                    M.sendParticles(_level, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity), M.getY(entity) + M.getBbHeight(entity) / 2.0F, M.getZ(entity), 20, 0.5, M.getBbHeight(entity) / 2.0F, 0.5, 0.02);
                }
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.THE_THING_DOG.get()), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                    }
                }
                if (world instanceof World _level && M.isClientSide(_level)) {
                    M.playLocalSound(_level, M.getX(entity), M.getY(entity), M.getZ(entity), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chestburster_kill")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
        }
    }
}
