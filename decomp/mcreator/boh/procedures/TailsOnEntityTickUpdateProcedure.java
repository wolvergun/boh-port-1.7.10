package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.SonicExeEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TailsOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world.getEntitiesOfClass(SonicExeEntity.class, AABB.ofSize(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).isEmpty() && !entity.level().isClientSide()) {
            entity.discard();
         }
      }
   }
}
