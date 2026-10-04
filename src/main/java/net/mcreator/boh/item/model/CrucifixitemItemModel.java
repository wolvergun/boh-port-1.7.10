package net.mcreator.boh.item.model;

import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.item.CrucifixitemItem;
import net.minecraft.util.ResourceLocation;

public class CrucifixitemItemModel extends GeoModel<CrucifixitemItem> {
    public ResourceLocation getAnimationResource(CrucifixitemItem animatable) {
        return new ResourceLocation("boh", "animations/crucifix.animation.json");
    }

    public ResourceLocation getModelResource(CrucifixitemItem animatable) {
        return new ResourceLocation("boh", "geo/crucifix.geo.json");
    }

    public ResourceLocation getTextureResource(CrucifixitemItem animatable) {
        return new ResourceLocation("boh", "textures/item/crucifix.png");
    }
}
