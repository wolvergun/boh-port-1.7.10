package net.mcreator.boh.world.dimension;

import net.mcreator.boh.procedures.BoilerRoomDimensionPlayerEntersDimensionProcedure;
import net.mcreator.boh.procedures.BoilerRoomDimensionPlayerLeavesDimensionProcedure;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber
public class BoilerRoomDimensionDimension {
   @SubscribeEvent
   public static void onPlayerChangedDimensionEvent(PlayerChangedDimensionEvent event) {
      Entity entity = event.getEntity();
      Level world = entity.level();
      double x = entity.getX();
      double y = entity.getY();
      double z = entity.getZ();
      if (event.getFrom() == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:boiler_room_dimension"))) {
         BoilerRoomDimensionPlayerLeavesDimensionProcedure.execute(world, entity);
      }

      if (event.getTo() == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:boiler_room_dimension"))) {
         BoilerRoomDimensionPlayerEntersDimensionProcedure.execute(world, x, y, z, entity);
      }
   }

   @EventBusSubscriber(bus = Bus.MOD)
   public static class BoilerRoomDimensionSpecialEffectsHandler {
      @SubscribeEvent
      @OnlyIn(Dist.CLIENT)
      public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
         DimensionSpecialEffects customEffect = new DimensionSpecialEffects(Float.NaN, true, SkyType.NONE, false, false) {
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
               return color;
            }

            public boolean isFoggyAt(int x, int y) {
               return true;
            }
         };
         event.register(new ResourceLocation("boh:boiler_room_dimension"), customEffect);
      }
   }
}
