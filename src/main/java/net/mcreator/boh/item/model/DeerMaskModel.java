package net.mcreator.boh.item.model;

import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.item.DeerMaskItem;
import net.minecraft.util.ResourceLocation;

public class DeerMaskModel extends GeoModel<DeerMaskItem> {
    public ResourceLocation getAnimationResource(DeerMaskItem object) {
        return new ResourceLocation("boh", "animations/wendigo_skull.animation.json");
    }

    public ResourceLocation getModelResource(DeerMaskItem object) {
        return new ResourceLocation("boh", "geo/wendigo_skull.geo.json");
    }

    public ResourceLocation getTextureResource(DeerMaskItem object) {
        return new ResourceLocation("boh", "textures/item/wendigo_skull.png");
    }
}
