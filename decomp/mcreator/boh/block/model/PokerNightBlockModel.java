package net.mcreator.boh.block.model;

import net.mcreator.boh.block.entity.PokerNightTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PokerNightBlockModel extends GeoModel<PokerNightTileEntity> {
   public ResourceLocation getAnimationResource(PokerNightTileEntity animatable) {
      return new ResourceLocation("boh", "animations/poker_night.animation.json");
   }

   public ResourceLocation getModelResource(PokerNightTileEntity animatable) {
      return new ResourceLocation("boh", "geo/poker_night.geo.json");
   }

   public ResourceLocation getTextureResource(PokerNightTileEntity animatable) {
      return new ResourceLocation("boh", "textures/block/poker_night.png");
   }
}
