package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.RollingGiantEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RollingGiantModel extends GeoModel<RollingGiantEntity> {
   public ResourceLocation getAnimationResource(RollingGiantEntity entity) {
      return new ResourceLocation("boh", "animations/giantmodel.animation.json");
   }

   public ResourceLocation getModelResource(RollingGiantEntity entity) {
      return new ResourceLocation("boh", "geo/giantmodel.geo.json");
   }

   public ResourceLocation getTextureResource(RollingGiantEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
