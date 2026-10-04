package net.mcreator.boh.item.model;

import net.mcreator.boh.item.GojiHeadItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GojiHeadModel extends GeoModel<GojiHeadItem> {
   public ResourceLocation getAnimationResource(GojiHeadItem object) {
      return new ResourceLocation("boh", "animations/goji_head.animation.json");
   }

   public ResourceLocation getModelResource(GojiHeadItem object) {
      return new ResourceLocation("boh", "geo/goji_head.geo.json");
   }

   public ResourceLocation getTextureResource(GojiHeadItem object) {
      return new ResourceLocation("boh", "textures/item/gogi.png");
   }
}
