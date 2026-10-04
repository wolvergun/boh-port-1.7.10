package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.FacehuggerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FacehuggerModel extends GeoModel<FacehuggerEntity> {
   public ResourceLocation getAnimationResource(FacehuggerEntity entity) {
      return new ResourceLocation("boh", "animations/facehugger.animation.json");
   }

   public ResourceLocation getModelResource(FacehuggerEntity entity) {
      return new ResourceLocation("boh", "geo/facehugger.geo.json");
   }

   public ResourceLocation getTextureResource(FacehuggerEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
