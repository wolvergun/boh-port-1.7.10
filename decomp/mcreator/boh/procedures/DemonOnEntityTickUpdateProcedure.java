package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class DemonOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.2, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.2));
         if (Math.random() < 0.5) {
            if (Math.random() < 0.025) {
               entity.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
            } else if (Math.random() < 0.025) {
               entity.setDeltaMovement(new Vec3(0.0, -1.0, 0.0));
            }
         }

         if (world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z))
            && world instanceof Level _lvl7
            && _lvl7.isDay()
            && entity.getRemainingFireTicks() < 0
            && !world.getLevelData().isRaining()) {
            entity.setSecondsOnFire(5);
         }
      }
   }
}
