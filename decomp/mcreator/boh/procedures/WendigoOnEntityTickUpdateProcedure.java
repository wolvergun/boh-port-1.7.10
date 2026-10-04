package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class WendigoOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z))
            && world instanceof Level _lvl1
            && _lvl1.isDay()
            && entity.getRemainingFireTicks() < 0
            && !world.getLevelData().isRaining()) {
            entity.setSecondsOnFire(5);
         }
      }
   }
}
