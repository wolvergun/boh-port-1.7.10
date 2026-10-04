package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.DemogorgonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DemogorgonModel extends GeoModel<DemogorgonEntity> {
   public ResourceLocation getAnimationResource(DemogorgonEntity entity) {
      return new ResourceLocation("boh", "animations/demogorgon.animation.json");
   }

   public ResourceLocation getModelResource(DemogorgonEntity entity) {
      return new ResourceLocation("boh", "geo/demogorgon.geo.json");
   }

   public ResourceLocation getTextureResource(DemogorgonEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
