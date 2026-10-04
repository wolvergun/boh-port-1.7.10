package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.DeerEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class DeerOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.isBaby())) {
            BohMod.queueServerWork(2, () -> entity.getPersistentData().putDouble("size", 1.0));
            if (Math.random() < 0.05) {
               if (!entity.level().isClientSide()) {
                  entity.discard();
               }

               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)BohModEntities.DEER_MIMIC.get())
                     .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setYRot(entity.getYRot());
                     entityToSpawn.setYBodyRot(entity.getYRot());
                     entityToSpawn.setYHeadRot(entity.getYRot());
                     entityToSpawn.setXRot(entity.getXRot());
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            }
         }

         if (Math.random() < 0.5) {
            BohMod.queueServerWork(2, () -> {
               if (entity instanceof DeerEntity animatable) {
                  animatable.setTexture("doe");
               }
            });
         }
      }
   }
}
