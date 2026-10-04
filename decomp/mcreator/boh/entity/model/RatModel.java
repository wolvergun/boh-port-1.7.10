package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.RatEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RatModel extends GeoModel<RatEntity> {
   public ResourceLocation getAnimationResource(RatEntity entity) {
      return new ResourceLocation("boh", "animations/rat.animation.json");
   }

   public ResourceLocation getModelResource(RatEntity entity) {
      return new ResourceLocation("boh", "geo/rat.geo.json");
   }

   public ResourceLocation getTextureResource(RatEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
