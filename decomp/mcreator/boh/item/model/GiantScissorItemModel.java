package net.mcreator.boh.item.model;

import net.mcreator.boh.item.GiantScissorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GiantScissorItemModel extends GeoModel<GiantScissorItem> {
   public ResourceLocation getAnimationResource(GiantScissorItem animatable) {
      return new ResourceLocation("boh", "animations/scissorman_scissor.animation.json");
   }

   public ResourceLocation getModelResource(GiantScissorItem animatable) {
      return new ResourceLocation("boh", "geo/scissorman_scissor.geo.json");
   }

   public ResourceLocation getTextureResource(GiantScissorItem animatable) {
      return new ResourceLocation("boh", "textures/item/giantscissor.png");
   }
}
