package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.VampireEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class VampireBatOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.35, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.35));
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.5, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.5));
            }
            if (Math.random() < 0.5) {
                if (Math.random() < 0.025) {
                    M.setDeltaMovement(entity, new Vec3(0.0, 1.0, 0.0));
                } else if (Math.random() < 0.025) {
                    M.setDeltaMovement(entity, new Vec3(0.0, -1.0, 0.0));
                }
            }
            if (M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z)) && world instanceof World _lvl12 && M.isDay(_lvl12) && M.getRemainingFireTicks(entity) < 0 && !M.isRaining(M.getLevelData(world))) {
                M.setSecondsOnFire(entity, 5);
            }
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob) && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true))) {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.VAMPIRE.get()), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setYRot(entityToSpawn, M.getYRot(entity));
                        M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                        M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                        M.setXRot(entityToSpawn, M.getXRot(entity));
                        M.setDeltaMovement(entityToSpawn, M.getDeltaMovement(entity).x(), M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z());
                    }
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    BohMod.queueServerWork(2, () -> {
                        if (entityiterator instanceof VampireEntity && entityiterator instanceof EntityLivingBase _entity) {
                            M.setHealth(_entity, entity instanceof EntityLivingBase _livEnt ? M.getHealth(_livEnt) : -1.0F);
                        }
                    });
                }
            }
        }
    }
}
