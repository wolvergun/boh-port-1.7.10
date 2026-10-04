package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown15Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Unown15Model extends GeoModel<Unown15Entity> {
   public ResourceLocation getAnimationResource(Unown15Entity entity) {
      return new ResourceLocation("boh", "animations/unown.animation.json");
   }

   public ResourceLocation getModelResource(Unown15Entity entity) {
      return new ResourceLocation("boh", "geo/unown.geo.json");
   }

   public ResourceLocation getTextureResource(Unown15Entity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
