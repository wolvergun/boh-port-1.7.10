package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.GrannyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GrannyModel extends GeoModel<GrannyEntity> {
   public ResourceLocation getAnimationResource(GrannyEntity entity) {
      return new ResourceLocation("boh", "animations/granny.animation.json");
   }

   public ResourceLocation getModelResource(GrannyEntity entity) {
      return new ResourceLocation("boh", "geo/granny.geo.json");
   }

   public ResourceLocation getTextureResource(GrannyEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
