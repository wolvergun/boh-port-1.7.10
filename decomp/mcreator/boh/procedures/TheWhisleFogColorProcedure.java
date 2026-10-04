package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent.ComputeFogColor;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(Dist.CLIENT)
public class TheWhisleFogColorProcedure {
   public static ComputeFogColor provider = null;

   public static void setColor(int color) {
      provider.setRed((color >> 16 & 0xFF) / 255.0F);
      provider.setGreen((color >> 8 & 0xFF) / 255.0F);
      provider.setBlue((color & 0xFF) / 255.0F);
   }

   public static void setColor(float level, int color) {
      if (!(level <= 0.0F)) {
         if (level >= 1.0F) {
            provider.setRed((color >> 16 & 0xFF) / 255.0F);
            provider.setGreen((color >> 8 & 0xFF) / 255.0F);
            provider.setBlue((color & 0xFF) / 255.0F);
         } else {
            level = Mth.clamp(level, 0.0F, 1.0F);
            provider.setRed(Mth.clamp(Mth.lerp(level, Mth.clamp(provider.getRed(), 0.0F, 1.0F), (color >> 16 & 0xFF) / 255.0F), 0.0F, 1.0F));
            provider.setGreen(Mth.clamp(Mth.lerp(level, Mth.clamp(provider.getGreen(), 0.0F, 1.0F), (color >> 8 & 0xFF) / 255.0F), 0.0F, 1.0F));
            provider.setBlue(Mth.clamp(Mth.lerp(level, Mth.clamp(provider.getBlue(), 0.0F, 1.0F), (color & 0xFF) / 255.0F), 0.0F, 1.0F));
         }
      }
   }

   @SubscribeEvent
   public static void computeFogColor(ComputeFogColor event) {
      provider = event;
      ClientLevel level = Minecraft.getInstance().level;
      Entity entity = provider.getCamera().getEntity();
      if (level != null && entity != null) {
         Vec3 entPos = entity.getPosition((float)provider.getPartialTick());
         execute(provider, entity);
      }
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if (entity.level().dimension() == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:baseplate_dimension"))
            && entity instanceof LivingEntity _livEnt3
            && _livEnt3.hasEffect((MobEffect)BohModMobEffects.THE_WHISLE.get())) {
            setColor(-16777216);
         }
      }
   }
}
