package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.display.PokerNightDisplayItem;
import net.mcreator.boh.block.model.PokerNightDisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PokerNightDisplayItemRenderer extends GeoItemRenderer<PokerNightDisplayItem> {
   public PokerNightDisplayItemRenderer() {
      super(new PokerNightDisplayModel());
   }

   public RenderType getRenderType(PokerNightDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }
}
