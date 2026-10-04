package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.NPC000Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NPC000Model extends GeoModel<NPC000Entity> {
   public ResourceLocation getAnimationResource(NPC000Entity entity) {
      return new ResourceLocation("boh", "animations/npc_000.animation.json");
   }

   public ResourceLocation getModelResource(NPC000Entity entity) {
      return new ResourceLocation("boh", "geo/npc_000.geo.json");
   }

   public ResourceLocation getTextureResource(NPC000Entity entity) {
      return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
   }
}
