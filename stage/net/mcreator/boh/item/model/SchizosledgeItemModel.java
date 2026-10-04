package net.mcreator.boh.item.model;

import net.mcreator.boh.item.SchizosledgeItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class SchizosledgeItemModel extends GeoModel<SchizosledgeItem> {

    public ResourceLocation getAnimationResource(SchizosledgeItem animatable) {
        return new ResourceLocation("boh", "animations/schizo_sledge.animation.json");
    }

    public ResourceLocation getModelResource(SchizosledgeItem animatable) {
        return new ResourceLocation("boh", "geo/schizo_sledge.geo.json");
    }

    public ResourceLocation getTextureResource(SchizosledgeItem animatable) {
        return new ResourceLocation("boh", "textures/item/simon_sledge.png");
    }
}
