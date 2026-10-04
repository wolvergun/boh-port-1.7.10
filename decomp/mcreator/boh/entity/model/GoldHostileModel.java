package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.GoldHostileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GoldHostileModel extends GeoModel<GoldHostileEntity> {
   public ResourceLocation getAnimationResource(GoldHostileEntity entity) {
      return new ResourceLocation("boh", "animations/gold_hostile.animation.json");
   }

   public ResourceLocation getModelResource(GoldHostileEntity entity) {
      return new ResourceLocation("boh", "geo/gold_hostile.geo.json");
   }

   public ResourceLocation getTextureResource(GoldHostileEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
