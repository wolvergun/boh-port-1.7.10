package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.entity.PokerNightTileEntity;
import net.mcreator.boh.block.model.PokerNightBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PokerNightTileRenderer extends GeoBlockRenderer<PokerNightTileEntity> {
   public PokerNightTileRenderer() {
      super(new PokerNightBlockModel());
   }

   public RenderType getRenderType(PokerNightTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }
}
