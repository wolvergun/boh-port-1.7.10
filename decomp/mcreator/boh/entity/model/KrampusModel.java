package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.KrampusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KrampusModel extends GeoModel<KrampusEntity> {
   public ResourceLocation getAnimationResource(KrampusEntity entity) {
      return new ResourceLocation("boh", "animations/krampus.animation.json");
   }

   public ResourceLocation getModelResource(KrampusEntity entity) {
      return new ResourceLocation("boh", "geo/krampus.geo.json");
   }

   public ResourceLocation getTextureResource(KrampusEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
