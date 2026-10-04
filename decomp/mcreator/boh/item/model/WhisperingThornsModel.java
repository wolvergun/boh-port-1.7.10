package net.mcreator.boh.item.model;

import net.mcreator.boh.item.WhisperingThornsItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WhisperingThornsModel extends GeoModel<WhisperingThornsItem> {
   public ResourceLocation getAnimationResource(WhisperingThornsItem object) {
      return new ResourceLocation("boh", "animations/whispering_thorns.animation.json");
   }

   public ResourceLocation getModelResource(WhisperingThornsItem object) {
      return new ResourceLocation("boh", "geo/whispering_thorns.geo.json");
   }

   public ResourceLocation getTextureResource(WhisperingThornsItem object) {
      return new ResourceLocation("boh", "textures/item/whispering_thorns.png");
   }
}
