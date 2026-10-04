package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.display.JarOWispDisplayItem;
import net.mcreator.boh.block.model.JarOWispDisplayModel;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.geo.GeoItemRenderer;
import net.minecraft.util.ResourceLocation;

public class JarOWispDisplayItemRenderer extends GeoItemRenderer<JarOWispDisplayItem> {
    public JarOWispDisplayItemRenderer() {
        super(new JarOWispDisplayModel());
    }

    public RenderType getRenderType(JarOWispDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
