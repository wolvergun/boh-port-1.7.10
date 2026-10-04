package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown1Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Unown1Model extends GeoModel<Unown1Entity> {
   public ResourceLocation getAnimationResource(Unown1Entity entity) {
      return new ResourceLocation("boh", "animations/unown.animation.json");
   }

   public ResourceLocation getModelResource(Unown1Entity entity) {
      return new ResourceLocation("boh", "geo/unown.geo.json");
   }

   public ResourceLocation getTextureResource(Unown1Entity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
