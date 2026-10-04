package net.mcreator.boh.item.model;

import net.mcreator.boh.item.SirenphoneItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class SirenphoneItemModel extends GeoModel<SirenphoneItem> {

    public ResourceLocation getAnimationResource(SirenphoneItem animatable) {
        return new ResourceLocation("boh", "animations/sirenphone.animation.json");
    }

    public ResourceLocation getModelResource(SirenphoneItem animatable) {
        return new ResourceLocation("boh", "geo/sirenphone.geo.json");
    }

    public ResourceLocation getTextureResource(SirenphoneItem animatable) {
        return new ResourceLocation("boh", "textures/item/sirenhead.png");
    }
}
