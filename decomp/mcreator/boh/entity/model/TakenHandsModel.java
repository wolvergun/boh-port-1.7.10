package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TakenHandsEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TakenHandsModel extends GeoModel<TakenHandsEntity> {
   public ResourceLocation getAnimationResource(TakenHandsEntity entity) {
      return new ResourceLocation("boh", "animations/taken_hands.animation.json");
   }

   public ResourceLocation getModelResource(TakenHandsEntity entity) {
      return new ResourceLocation("boh", "geo/taken_hands.geo.json");
   }

   public ResourceLocation getTextureResource(TakenHandsEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
