package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BloodwaveEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BloodwaveModel extends GeoModel<BloodwaveEntity> {
   public ResourceLocation getAnimationResource(BloodwaveEntity entity) {
      return new ResourceLocation("boh", "animations/bloodwave.animation.json");
   }

   public ResourceLocation getModelResource(BloodwaveEntity entity) {
      return new ResourceLocation("boh", "geo/bloodwave.geo.json");
   }

   public ResourceLocation getTextureResource(BloodwaveEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
