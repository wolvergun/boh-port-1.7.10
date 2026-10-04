package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class ChuckyGrabEntityIsHurtProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (sourceentity instanceof Player && Math.random() < 0.25) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.CHUCKY.get()).spawn(_level, BlockPos.containing(x, y + 2.0, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
               }
            }

            if (!entity.level().isClientSide()) {
               entity.discard();
            }

            if (sourceentity instanceof LivingEntity _entity) {
               _entity.removeEffect((MobEffect)BohModMobEffects.EFFECT_CHUCKY_GRAB.get());
            }
         }
      }
   }
}
