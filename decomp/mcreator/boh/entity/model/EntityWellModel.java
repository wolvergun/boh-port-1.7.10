package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.EntityWellEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EntityWellModel extends GeoModel<EntityWellEntity> {
   public ResourceLocation getAnimationResource(EntityWellEntity entity) {
      return new ResourceLocation("boh", "animations/the_rake.animation.json");
   }

   public ResourceLocation getModelResource(EntityWellEntity entity) {
      return new ResourceLocation("boh", "geo/the_rake.geo.json");
   }

   public ResourceLocation getTextureResource(EntityWellEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
