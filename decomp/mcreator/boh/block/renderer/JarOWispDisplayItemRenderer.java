package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.display.JarOWispDisplayItem;
import net.mcreator.boh.block.model.JarOWispDisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class JarOWispDisplayItemRenderer extends GeoItemRenderer<JarOWispDisplayItem> {
   public JarOWispDisplayItemRenderer() {
      super(new JarOWispDisplayModel());
   }

   public RenderType getRenderType(JarOWispDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }
}
