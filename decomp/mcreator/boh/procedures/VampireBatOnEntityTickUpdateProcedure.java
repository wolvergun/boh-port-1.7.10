package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.VampireEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class VampireBatOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.35, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.35));
         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.5, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.5));
         }

         if (Math.random() < 0.5) {
            if (Math.random() < 0.025) {
               entity.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
            } else if (Math.random() < 0.025) {
               entity.setDeltaMovement(new Vec3(0.0, -1.0, 0.0));
            }
         }

         if (world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z))
            && world instanceof Level _lvl12
            && _lvl12.isDay()
            && entity.getRemainingFireTicks() < 0
            && !world.getLevelData().isRaining()) {
            entity.setSecondsOnFire(5);
         }

         if (entity instanceof Mob _mob
            && _mob.isAggressive()
            && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true).isEmpty()) {
            if (!entity.level().isClientSide()) {
               entity.discard();
            }

            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.VAMPIRE.get())
                  .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setYRot(entity.getYRot());
                  entityToSpawn.setYBodyRot(entity.getYRot());
                  entityToSpawn.setYHeadRot(entity.getYRot());
                  entityToSpawn.setXRot(entity.getXRot());
                  entityToSpawn.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y(), entity.getDeltaMovement().z());
               }
            }

            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               BohMod.queueServerWork(2, () -> {
                  if (entityiterator instanceof VampireEntity && entityiterator instanceof LivingEntity _entity) {
                     _entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F);
                  }
               });
            }
         }
      }
   }
}
