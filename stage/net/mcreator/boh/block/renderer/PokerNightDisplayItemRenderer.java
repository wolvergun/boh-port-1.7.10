package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.display.PokerNightDisplayItem;
import net.mcreator.boh.block.model.PokerNightDisplayModel;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoItemRenderer;

public class PokerNightDisplayItemRenderer extends GeoItemRenderer<PokerNightDisplayItem> {

    public PokerNightDisplayItemRenderer() {
        super(new PokerNightDisplayModel());
    }

    public RenderType getRenderType(PokerNightDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
