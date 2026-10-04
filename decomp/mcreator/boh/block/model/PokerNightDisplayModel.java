package net.mcreator.boh.block.model;

import net.mcreator.boh.block.display.PokerNightDisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PokerNightDisplayModel extends GeoModel<PokerNightDisplayItem> {
   public ResourceLocation getAnimationResource(PokerNightDisplayItem animatable) {
      return new ResourceLocation("boh", "animations/poker_night.animation.json");
   }

   public ResourceLocation getModelResource(PokerNightDisplayItem animatable) {
      return new ResourceLocation("boh", "geo/poker_night.geo.json");
   }

   public ResourceLocation getTextureResource(PokerNightDisplayItem entity) {
      return new ResourceLocation("boh", "textures/block/poker_night.png");
   }
}
