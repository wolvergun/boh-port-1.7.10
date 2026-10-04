package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.GraftonMonsterEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GraftonMonsterModel extends GeoModel<GraftonMonsterEntity> {
   public ResourceLocation getAnimationResource(GraftonMonsterEntity entity) {
      return new ResourceLocation("boh", "animations/grafton_monster.animation.json");
   }

   public ResourceLocation getModelResource(GraftonMonsterEntity entity) {
      return new ResourceLocation("boh", "geo/grafton_monster.geo.json");
   }

   public ResourceLocation getTextureResource(GraftonMonsterEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
