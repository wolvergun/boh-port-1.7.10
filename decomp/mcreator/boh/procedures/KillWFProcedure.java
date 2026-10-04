package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.WhitefaceEntity;
import net.mcreator.boh.entity.WhitefaceFriendlyEntity;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class KillWFProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (BohModVariables.MapVariables.get(world).Kill_WF == 1.0
            && (entity instanceof WhitefaceEntity || entity instanceof WhitefaceFriendlyEntity)
            && !entity.level().isClientSide()) {
            entity.discard();
         }
      }
   }
}
