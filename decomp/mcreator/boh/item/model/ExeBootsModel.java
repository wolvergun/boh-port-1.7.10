package net.mcreator.boh.item.model;

import net.mcreator.boh.item.ExeBootsItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ExeBootsModel extends GeoModel<ExeBootsItem> {
   public ResourceLocation getAnimationResource(ExeBootsItem object) {
      return new ResourceLocation("boh", "animations/exe_boots.animation.json");
   }

   public ResourceLocation getModelResource(ExeBootsItem object) {
      return new ResourceLocation("boh", "geo/exe_boots.geo.json");
   }

   public ResourceLocation getTextureResource(ExeBootsItem object) {
      return new ResourceLocation("boh", "textures/item/exe.png");
   }
}
