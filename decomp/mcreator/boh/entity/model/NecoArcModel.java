package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.NecoArcEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NecoArcModel extends GeoModel<NecoArcEntity> {
   public ResourceLocation getAnimationResource(NecoArcEntity entity) {
      return new ResourceLocation("boh", "animations/necoarc.animation.json");
   }

   public ResourceLocation getModelResource(NecoArcEntity entity) {
      return new ResourceLocation("boh", "geo/necoarc.geo.json");
   }

   public ResourceLocation getTextureResource(NecoArcEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
