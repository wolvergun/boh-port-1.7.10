package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class GoldHostileEntityDiesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      BohModVariables.MapVariables.get(world).spawn_gold = 0.0;
      BohModVariables.MapVariables.get(world).syncData(world);
      if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = ((EntityType)BohModEntities.UNOWN_4.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
         if (entityToSpawn != null) {
            entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
         }
      }

      if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = ((EntityType)BohModEntities.UNOWN_3.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
         if (entityToSpawn != null) {
            entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
         }
      }

      if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = ((EntityType)BohModEntities.UNOWN_1.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
         if (entityToSpawn != null) {
            entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
         }
      }

      if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = ((EntityType)BohModEntities.UNOWN_1.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
         if (entityToSpawn != null) {
            entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
         }
      }
   }
}
