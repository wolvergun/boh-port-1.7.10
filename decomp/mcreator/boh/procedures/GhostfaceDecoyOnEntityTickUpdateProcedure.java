package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.GhostfaceEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GhostfaceDecoyOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((
               world.getEntitiesOfClass(GhostfaceEntity.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()
                  || world.getEntitiesOfClass(GhostfaceEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true).isEmpty()
            )
            && !entity.level().isClientSide()) {
            entity.discard();
         }
      }
   }
}
