package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class TurnOffRainBackroomsProcedure {
   @SubscribeEvent
   public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
      execute(event, event.getEntity().level(), event.getDimension());
   }

   public static void execute(LevelAccessor world, ResourceKey<Level> dimension) {
      execute(null, world, dimension);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, ResourceKey<Level> dimension) {
      if (dimension != null) {
         if (dimension == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:level_0"))) {
            world.getLevelData().setRaining(false);
         }
      }
   }
}
