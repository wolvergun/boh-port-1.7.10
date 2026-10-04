package net.mcreator.boh.block.renderer;

import net.mcreator.boh.block.entity.PokerNightTileEntity;
import net.mcreator.boh.block.model.PokerNightBlockModel;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.geo.GeoBlockRenderer;
import net.minecraft.util.ResourceLocation;

public class PokerNightTileRenderer extends GeoBlockRenderer<PokerNightTileEntity> {
    public PokerNightTileRenderer() {
        super(new PokerNightBlockModel());
    }

    public RenderType getRenderType(PokerNightTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
