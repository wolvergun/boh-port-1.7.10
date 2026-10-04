package net.mcreator.boh.item.model;

import net.mcreator.boh.item.PaintedSwordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PaintedSwordItemModel extends GeoModel<PaintedSwordItem> {
   public ResourceLocation getAnimationResource(PaintedSwordItem animatable) {
      return new ResourceLocation("boh", "animations/painted_sword.animation.json");
   }

   public ResourceLocation getModelResource(PaintedSwordItem animatable) {
      return new ResourceLocation("boh", "geo/painted_sword.geo.json");
   }

   public ResourceLocation getTextureResource(PaintedSwordItem animatable) {
      return new ResourceLocation("boh", "textures/item/painted_sword.png");
   }
}
