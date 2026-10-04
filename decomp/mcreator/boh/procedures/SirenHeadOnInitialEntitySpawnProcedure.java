package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.SirenHeadEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class SirenHeadOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         BohModVariables.MapVariables.get(world).spawn_siren = 1.0;
         BohModVariables.MapVariables.get(world).syncData(world);
         if (entity instanceof SirenHeadEntity && Math.random() < 0.5) {
            if (!entity.level().isClientSide()) {
               entity.discard();
            }

            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.LIGHT_HEAD.get())
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
   }
}
