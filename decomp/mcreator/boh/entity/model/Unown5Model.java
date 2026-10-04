package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown5Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Unown5Model extends GeoModel<Unown5Entity> {
   public ResourceLocation getAnimationResource(Unown5Entity entity) {
      return new ResourceLocation("boh", "animations/unown.animation.json");
   }

   public ResourceLocation getModelResource(Unown5Entity entity) {
      return new ResourceLocation("boh", "geo/unown.geo.json");
   }

   public ResourceLocation getTextureResource(Unown5Entity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
