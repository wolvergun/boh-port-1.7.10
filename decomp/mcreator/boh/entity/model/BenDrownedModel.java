package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BenDrownedEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BenDrownedModel extends GeoModel<BenDrownedEntity> {
   public ResourceLocation getAnimationResource(BenDrownedEntity entity) {
      return new ResourceLocation("boh", "animations/bendrowned.animation.json");
   }

   public ResourceLocation getModelResource(BenDrownedEntity entity) {
      return new ResourceLocation("boh", "geo/bendrowned.geo.json");
   }

   public ResourceLocation getTextureResource(BenDrownedEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
