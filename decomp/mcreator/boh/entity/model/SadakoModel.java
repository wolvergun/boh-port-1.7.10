package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SadakoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SadakoModel extends GeoModel<SadakoEntity> {
   public ResourceLocation getAnimationResource(SadakoEntity entity) {
      return new ResourceLocation("boh", "animations/sadako.animation.json");
   }

   public ResourceLocation getModelResource(SadakoEntity entity) {
      return new ResourceLocation("boh", "geo/sadako.geo.json");
   }

   public ResourceLocation getTextureResource(SadakoEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
