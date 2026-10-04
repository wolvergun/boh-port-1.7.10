package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.VitaMimicEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;

public class VitaMimicOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world.getBrightness(LightLayer.BLOCK, BlockPos.containing(x, y, z)) <= 8
            ^ (!(world instanceof Level _lvl1 && _lvl1.isDay()) && world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z)))) {
            if (entity instanceof VitaMimicEntity animatable) {
               animatable.setTexture("vita_mimic_translucent");
            }
         } else if (entity instanceof VitaMimicEntity animatable) {
            animatable.setTexture("vita_mimic");
         }
      }
   }
}
