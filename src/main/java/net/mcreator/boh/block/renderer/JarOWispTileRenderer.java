package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.entity.JarOWispTileEntity;
import net.mcreator.boh.block.model.JarOWispBlockModel;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.geo.GeoBlockRenderer;
import net.minecraft.util.ResourceLocation;

public class JarOWispTileRenderer extends GeoBlockRenderer<JarOWispTileEntity> {
    public JarOWispTileRenderer() {
        super(new JarOWispBlockModel());
    }

    public RenderType getRenderType(JarOWispTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
