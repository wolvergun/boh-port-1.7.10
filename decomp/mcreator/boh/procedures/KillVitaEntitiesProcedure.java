package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.TrimmingEntity;
import net.mcreator.boh.entity.VitaMimicEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class KillVitaEntitiesProcedure {
   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, final LevelAccessor world, final double x, final double y, final double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof VitaMimicEntity || entity instanceof TrimmingEntity) {
            (new Object() {
               void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModBlocks.VITA_CRAWL.get()));
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
