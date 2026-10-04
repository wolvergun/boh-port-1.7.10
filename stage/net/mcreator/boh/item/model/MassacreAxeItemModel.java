package net.mcreator.boh.item.model;

import net.mcreator.boh.item.MassacreAxeItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class MassacreAxeItemModel extends GeoModel<MassacreAxeItem> {

    public ResourceLocation getAnimationResource(MassacreAxeItem animatable) {
        return new ResourceLocation("boh", "animations/massacre_axe.animation.json");
    }

    public ResourceLocation getModelResource(MassacreAxeItem animatable) {
        return new ResourceLocation("boh", "geo/massacre_axe.geo.json");
    }

    public ResourceLocation getTextureResource(MassacreAxeItem animatable) {
        return new ResourceLocation("boh", "textures/item/massacre_axe.png");
    }
}
