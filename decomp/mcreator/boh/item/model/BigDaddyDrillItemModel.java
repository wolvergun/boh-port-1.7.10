package net.mcreator.boh.item.model;

import net.mcreator.boh.item.BigDaddyDrillItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BigDaddyDrillItemModel extends GeoModel<BigDaddyDrillItem> {
   public ResourceLocation getAnimationResource(BigDaddyDrillItem animatable) {
      return new ResourceLocation("boh", "animations/big_daddy_drill.animation.json");
   }

   public ResourceLocation getModelResource(BigDaddyDrillItem animatable) {
      return new ResourceLocation("boh", "geo/big_daddy_drill.geo.json");
   }

   public ResourceLocation getTextureResource(BigDaddyDrillItem animatable) {
      return new ResourceLocation("boh", "textures/item/big_daddy_drill.png");
   }
}
