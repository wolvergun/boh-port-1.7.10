package net.mcreator.boh.item.model;

import net.mcreator.boh.item.RayGunItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class RayGunItemModel extends GeoModel<RayGunItem> {

    public ResourceLocation getAnimationResource(RayGunItem animatable) {
        return new ResourceLocation("boh", "animations/raygun.animation.json");
    }

    public ResourceLocation getModelResource(RayGunItem animatable) {
        return new ResourceLocation("boh", "geo/raygun.geo.json");
    }

    public ResourceLocation getTextureResource(RayGunItem animatable) {
        return new ResourceLocation("boh", "textures/item/raygun.png");
    }
}
