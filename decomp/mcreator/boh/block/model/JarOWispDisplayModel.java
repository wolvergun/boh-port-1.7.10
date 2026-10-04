package net.mcreator.boh.block.model;

import net.mcreator.boh.block.display.JarOWispDisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class JarOWispDisplayModel extends GeoModel<JarOWispDisplayItem> {
   public ResourceLocation getAnimationResource(JarOWispDisplayItem animatable) {
      return new ResourceLocation("boh", "animations/jar_o_wisp.animation.json");
   }

   public ResourceLocation getModelResource(JarOWispDisplayItem animatable) {
      return new ResourceLocation("boh", "geo/jar_o_wisp.geo.json");
   }

   public ResourceLocation getTextureResource(JarOWispDisplayItem entity) {
      return new ResourceLocation("boh", "textures/block/pot_o_wisp.png");
   }
}
