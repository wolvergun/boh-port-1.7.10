package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.CartoonCatEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CartoonCatModel extends GeoModel<CartoonCatEntity> {
   public ResourceLocation getAnimationResource(CartoonCatEntity entity) {
      return new ResourceLocation("boh", "animations/cartoon_cat.animation.json");
   }

   public ResourceLocation getModelResource(CartoonCatEntity entity) {
      return new ResourceLocation("boh", "geo/cartoon_cat.geo.json");
   }

   public ResourceLocation getTextureResource(CartoonCatEntity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
