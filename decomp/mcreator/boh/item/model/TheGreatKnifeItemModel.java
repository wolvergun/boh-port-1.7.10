package net.mcreator.boh.item.model;

import net.mcreator.boh.item.TheGreatKnifeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheGreatKnifeItemModel extends GeoModel<TheGreatKnifeItem> {
   public ResourceLocation getAnimationResource(TheGreatKnifeItem animatable) {
      return new ResourceLocation("boh", "animations/the_great_knife.animation.json");
   }

   public ResourceLocation getModelResource(TheGreatKnifeItem animatable) {
      return new ResourceLocation("boh", "geo/the_great_knife.geo.json");
   }

   public ResourceLocation getTextureResource(TheGreatKnifeItem animatable) {
      return new ResourceLocation("boh", "textures/item/great_knife.png");
   }
}
