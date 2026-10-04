package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.ExeMonitorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ExeMonitorModel extends GeoModel<ExeMonitorEntity> {
   public ResourceLocation getAnimationResource(ExeMonitorEntity entity) {
      return new ResourceLocation("boh", "animations/exe_monitor.animation.json");
   }

   public ResourceLocation getModelResource(ExeMonitorEntity entity) {
      return new ResourceLocation("boh", "geo/exe_monitor.geo.json");
   }

   public ResourceLocation getTextureResource(ExeMonitorEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
