package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.NewbornEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NewbornModel extends GeoModel<NewbornEntity> {
   public ResourceLocation getAnimationResource(NewbornEntity entity) {
      return new ResourceLocation("boh", "animations/newborn.animation.json");
   }

   public ResourceLocation getModelResource(NewbornEntity entity) {
      return new ResourceLocation("boh", "geo/newborn.geo.json");
   }

   public ResourceLocation getTextureResource(NewbornEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
