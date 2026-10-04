package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.LittleSisterEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HasLSAroundProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return !world.getEntitiesOfClass(LittleSisterEntity.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true).isEmpty();
   }
}
