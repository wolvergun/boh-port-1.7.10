package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;

public class JasonVoorheesEntityDiesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = ((EntityType)BohModEntities.JASON_MASK.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
         if (entityToSpawn != null) {
            entityToSpawn.setYRot((float)Mth.nextDouble(RandomSource.create(), 1.0, 360.0));
            entityToSpawn.setYBodyRot((float)Mth.nextDouble(RandomSource.create(), 1.0, 360.0));
            entityToSpawn.setYHeadRot((float)Mth.nextDouble(RandomSource.create(), 1.0, 360.0));
            entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
         }
      }

      if (Math.random() < 0.25 && world instanceof ServerLevel _level) {
         ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.MACHETE.get()));
         entityToSpawn.setPickUpDelay(10);
         _level.addFreshEntity(entityToSpawn);
      }
   }
}
