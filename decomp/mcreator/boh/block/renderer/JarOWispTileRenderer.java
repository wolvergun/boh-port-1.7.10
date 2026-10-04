package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.entity.JarOWispTileEntity;
import net.mcreator.boh.block.model.JarOWispBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class JarOWispTileRenderer extends GeoBlockRenderer<JarOWispTileEntity> {
   public JarOWispTileRenderer() {
      super(new JarOWispBlockModel());
   }

   public RenderType getRenderType(JarOWispTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }
}
