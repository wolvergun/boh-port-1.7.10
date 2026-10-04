package net.mcreator.boh.item.model;

import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.item.BoomStickItem;
import net.minecraft.util.ResourceLocation;

public class BoomStickItemModel extends GeoModel<BoomStickItem> {
    public ResourceLocation getAnimationResource(BoomStickItem animatable) {
        return new ResourceLocation("boh", "animations/shotgun.animation.json");
    }

    public ResourceLocation getModelResource(BoomStickItem animatable) {
        return new ResourceLocation("boh", "geo/shotgun.geo.json");
    }

    public ResourceLocation getTextureResource(BoomStickItem animatable) {
        return new ResourceLocation("boh", "textures/item/shotgun.png");
    }
}
