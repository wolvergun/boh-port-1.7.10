package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;

public class BlackWalnutLeavesBlockDestroyedByPlayerProcedure {
   public static void execute(final LevelAccessor world, final double x, final double y, final double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() != Items.SHEARS) {
            if (Math.random() < 0.05 && world instanceof ServerLevel _level) {
               ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModBlocks.BLACK_WALLNUT_SAPPLING.get()));
               entityToSpawn.setPickUpDelay(10);
               _level.addFreshEntity(entityToSpawn);
            }

            if (Math.random() < 0.1) {
               (new Object() {
                  void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.WALNUT.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }

                     int tick2 = ticks;
                     BohMod.queueServerWork(tick2, () -> {
                        if (timedlooptotal > timedloopiterator + 1) {
                           this.timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
                        }
                     });
                  }
               }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 4.0), 1);
            }
         }
      }
   }
}
