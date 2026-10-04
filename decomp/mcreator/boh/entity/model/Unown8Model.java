package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown8Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Unown8Model extends GeoModel<Unown8Entity> {
   public ResourceLocation getAnimationResource(Unown8Entity entity) {
      return new ResourceLocation("boh", "animations/unown.animation.json");
   }

   public ResourceLocation getModelResource(Unown8Entity entity) {
      return new ResourceLocation("boh", "geo/unown.geo.json");
   }

   public ResourceLocation getTextureResource(Unown8Entity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
