package net.mcreator.boh.item.model;

import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.item.GojiHeadItem;
import net.minecraft.util.ResourceLocation;

public class GojiHeadModel extends GeoModel<GojiHeadItem> {
    public ResourceLocation getAnimationResource(GojiHeadItem object) {
        return new ResourceLocation("boh", "animations/goji_head.animation.json");
    }

    public ResourceLocation getModelResource(GojiHeadItem object) {
        return new ResourceLocation("boh", "geo/goji_head.geo.json");
    }

    public ResourceLocation getTextureResource(GojiHeadItem object) {
        return new ResourceLocation("boh", "textures/item/gogi.png");
    }
}
