package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.FuwattiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FuwattiModel extends GeoModel<FuwattiEntity> {
   public ResourceLocation getAnimationResource(FuwattiEntity entity) {
      return new ResourceLocation("boh", "animations/fuwatti.animation.json");
   }

   public ResourceLocation getModelResource(FuwattiEntity entity) {
      return new ResourceLocation("boh", "geo/fuwatti.geo.json");
   }

   public ResourceLocation getTextureResource(FuwattiEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
