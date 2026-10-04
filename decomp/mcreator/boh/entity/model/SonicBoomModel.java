package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SonicBoomEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SonicBoomModel extends GeoModel<SonicBoomEntity> {
   public ResourceLocation getAnimationResource(SonicBoomEntity entity) {
      return new ResourceLocation("boh", "animations/sonic_boom.animation.json");
   }

   public ResourceLocation getModelResource(SonicBoomEntity entity) {
      return new ResourceLocation("boh", "geo/sonic_boom.geo.json");
   }

   public ResourceLocation getTextureResource(SonicBoomEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
