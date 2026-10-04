package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown14Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Unown14Model extends GeoModel<Unown14Entity> {
   public ResourceLocation getAnimationResource(Unown14Entity entity) {
      return new ResourceLocation("boh", "animations/unown.animation.json");
   }

   public ResourceLocation getModelResource(Unown14Entity entity) {
      return new ResourceLocation("boh", "geo/unown.geo.json");
   }

   public ResourceLocation getTextureResource(Unown14Entity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
