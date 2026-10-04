package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BigDaddyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BigDaddyModel extends GeoModel<BigDaddyEntity> {
   public ResourceLocation getAnimationResource(BigDaddyEntity entity) {
      return new ResourceLocation("boh", "animations/big_daddy.animation.json");
   }

   public ResourceLocation getModelResource(BigDaddyEntity entity) {
      return new ResourceLocation("boh", "geo/big_daddy.geo.json");
   }

   public ResourceLocation getTextureResource(BigDaddyEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
