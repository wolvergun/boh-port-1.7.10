package net.mcreator.boh.item.model;

import net.mcreator.boh.item.StopSignItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class StopSignItemModel extends GeoModel<StopSignItem> {

    public ResourceLocation getAnimationResource(StopSignItem animatable) {
        return new ResourceLocation("boh", "animations/stop_sign.animation.json");
    }

    public ResourceLocation getModelResource(StopSignItem animatable) {
        return new ResourceLocation("boh", "geo/stop_sign.geo.json");
    }

    public ResourceLocation getTextureResource(StopSignItem animatable) {
        return new ResourceLocation("boh", "textures/item/stop_sign.png");
    }
}
