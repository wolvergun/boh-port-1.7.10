package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.KrasueEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KrasueModel extends GeoModel<KrasueEntity> {
   public ResourceLocation getAnimationResource(KrasueEntity entity) {
      return new ResourceLocation("boh", "animations/krasue.animation.json");
   }

   public ResourceLocation getModelResource(KrasueEntity entity) {
      return new ResourceLocation("boh", "geo/krasue.geo.json");
   }

   public ResourceLocation getTextureResource(KrasueEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
