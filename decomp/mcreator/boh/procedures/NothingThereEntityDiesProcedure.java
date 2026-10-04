package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;

public class NothingThereEntityDiesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (Math.random() < 0.21 && world instanceof ServerLevel _level) {
         ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.MIMICRY.get()));
         entityToSpawn.setPickUpDelay(10);
         _level.addFreshEntity(entityToSpawn);
      }
   }
}
