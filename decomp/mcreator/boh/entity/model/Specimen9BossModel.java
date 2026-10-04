package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Specimen9BossEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Specimen9BossModel extends GeoModel<Specimen9BossEntity> {
   public ResourceLocation getAnimationResource(Specimen9BossEntity entity) {
      return new ResourceLocation("boh", "animations/specimen9_boss.animation.json");
   }

   public ResourceLocation getModelResource(Specimen9BossEntity entity) {
      return new ResourceLocation("boh", "geo/specimen9_boss.geo.json");
   }

   public ResourceLocation getTextureResource(Specimen9BossEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
