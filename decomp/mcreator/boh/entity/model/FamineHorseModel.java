package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.FamineHorseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FamineHorseModel extends GeoModel<FamineHorseEntity> {
   public ResourceLocation getAnimationResource(FamineHorseEntity entity) {
      return new ResourceLocation("boh", "animations/4horse.animation.json");
   }

   public ResourceLocation getModelResource(FamineHorseEntity entity) {
      return new ResourceLocation("boh", "geo/4horse.geo.json");
   }

   public ResourceLocation getTextureResource(FamineHorseEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
